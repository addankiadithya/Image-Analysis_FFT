/*
 * Final Project: Image Analysis and Compression using FFT
 *
 * Part I: Image Analysis using FFT
 * --------------------------------
 * 
 * Advanced Algorithms- Dr. Zhong-Hui Duan
 * 
 * Image Analysis module generates the phase and magnitude images after calculating 
 * the FFT of the image matrix.  
 * Inputs : Image -PGM of type P2(ASCII) and Filter value
 *   
 * Outputs : i. PGM Images for 
 * 				1. Phase, 
 * 				2. Magnitude and 
 * 				3. Reconstructed image after performing inverse FFT
 * 			 ii. FFT file for storing the required FFT result values based on filter value.
 * 
 * Part II: Image Compression using FFT
 * ------------------------------------
 * 
 * Image compression is done by analysing the required values to be stored from the 
 * resultant FFT matrix and performing a DFT for the image to show the reconstruction capacity.
 * 
 * Inputs : FFT file from Part I
 * 
 * Outputs : Reconstructed image from the saved values.
 *  
 * Author : Adithya Addanki
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import edu.emory.mathcs.jtransforms.fft.DoubleFFT_2D;

public class FFT {
	static int picWidth = 0;
	static int picHeight = 0;

	public static void DoubleFFT_2D(int rows, int columns) {
		int picWidth;
		int picHeight;
	}

	public static void main(String[] args) {
		long startTime=0,endTime=0;
		int choice = Integer.parseInt(args[0]);
		String filePath = args[1];
		if(choice==1){
			Scanner scanner;
			try {
				startTime=System.currentTimeMillis();
				scanner = new Scanner(new File(filePath));
				int n=Integer.parseInt(args[2]);
				int max = 0;
				String header = scanner.nextLine();
				String comment = scanner.nextLine();
				picWidth = scanner.nextInt();
				picHeight = scanner.nextInt();
				if(n>picHeight && n>picWidth)
					throw new Exception();
				max = scanner.nextInt();
				double[][] data2D = new double[picHeight][2*picWidth];
				//System.out.print("Given Image is:");
				//System.out.print("\n");
				//System.out.print("\n");
				for (int row = 0; row < picHeight; row++) {
					for (int col = 0; col < picWidth; col++) {
						data2D[row][col] = scanner.nextInt();
					}
				}
				// Apply FFT on the given Image
				DoubleFFT_2D d = new DoubleFFT_2D(picHeight, picWidth);
				d.complexForward(data2D);
				double[][] data2DCopy = new double[picHeight][2*picWidth];
				double[][] data2D_Updated = new double[picHeight][2*picWidth];
				data2DCopy=data2D;
				double mag[][]=new double[picHeight][picWidth];
				double phase[][]=new double[picHeight][picWidth];
				double magcopy[][]=new double[picHeight][picWidth];
				double phasecopy[][]=new double[picHeight][picWidth];
				//			System.out.print("Sine and Cosine of Given Matrix:");
				//			System.out.println("Magnitudes");
				int magi=0,magj=0;
				for (int rowm = 0; rowm < picHeight; rowm++) {
					for (int colm = 0; colm <picWidth*2; colm=colm+2) {
						double img=data2DCopy[rowm][colm+1];
						double rl=data2DCopy[rowm][colm];
						//System.out.println(img+"  "+rl+";" );
						double rl2=rl*rl;
						double img2=img*img;
						double logVal=Math.log(Math.round(Math.sqrt(rl2+img2)));
						magcopy[magi][magj]=Math.sqrt(rl2+img2);
						mag[magi][magj]=logVal*logVal;
						//mag[magi][magj]=Math.log(Math.round(Math.sqrt(rl2+img2)))/Math.log(2);
						//					System.out.print(mag[magi][magj]+"   ");
						if(magj==picWidth-1)
						{
							magj=0;
							++magi;
						}
						else
							magj++;
					}
					//			System.out.println();
				}
				//System.out.println("Phases:");
				magi=0;magj=0;
				for (int row = 0; row < picHeight; row++) {
					for (int col = 0; col <2* picWidth; col=col+2) {
						double img=data2D[row][col+1];
						double rl=data2D[row][col];
						if(rl>0){
							phase[magi][magj]=Math.toDegrees(Math.atan(img/rl))+360;
							phasecopy[magi][magj]=Math.toDegrees(Math.atan(img/rl));						
						}
						else if(rl<0 && img>=0){
							phase[magi][magj]=Math.toDegrees(Math.atan(img/rl)+Math.PI)+360;
							phasecopy[magi][magj]=Math.toDegrees(Math.atan(img/rl)+Math.PI);
						}
						else if(rl<0 && img<0){
							phase[magi][magj]=Math.toDegrees(Math.atan(img/rl)-Math.PI)+360;
							phasecopy[magi][magj]=Math.toDegrees(Math.atan(img/rl)-Math.PI);
						}
						else if(rl==0 && img>0){
							phase[magi][magj]=Math.toDegrees(Math.PI/2)+360;
							phasecopy[magi][magj]=Math.toDegrees(Math.PI/2);
						}
						else if(rl==0 && img<0){
							phase[magi][magj]=Math.toDegrees(-Math.PI/2)+360;
							phasecopy[magi][magj]=Math.toDegrees(-Math.PI/2);
						}
						else
						{
							phase[magi][magj]=360;
							phasecopy[magi][magj]=0;
						}
						phase[magi][magj]=Math.round((phase[magi][magj]%360)*255/360);
						//System.out.print(phase[magi][magj]+"   ");
						if(magj==picWidth-1)
						{
							magj=0;
							++magi;
						}
						else
							magj++;
					}
					//System.out.println();
				}
				double imgfil=0;
				double realfil=0;
				double filterFre=0;
				int number=0;
				for (int rowm = 0; rowm < picHeight; rowm++) {
					for (int colm = 0; colm <picWidth; colm++) {
						//System.out.println(rowm+"  "+colm+"  "+n);
						if((rowm<n && colm< (n))||(rowm>picHeight-n-1 && colm < (n))||(rowm<n && colm>(picWidth-(n)-1))||(rowm>(picHeight-n-1) && colm>(picWidth-(n)-1)))
						{
							//System.out.println(rowm+"  "+colm+"  "+n+" -->  "+magcopy[rowm][colm]);
							//System.out.println(data2DCopy[rowm][colm*2]+" -->  "+data2DCopy[rowm][(colm*2)+1]);
							filterFre+=magcopy[rowm][colm];
							realfil+=data2DCopy[rowm][colm*2];
							imgfil+=data2DCopy[rowm][(colm*2)+1];
							number++;
						}
					}
				}
				double[][] datareal=new double[picHeight][picWidth];
				double[][] dataimg=new double[picHeight][picWidth];
				for (int rowm = 0; rowm < picHeight; rowm++) {
					for (int colm = 0; colm <picWidth; colm++) {
						//int j=colm/2;
						datareal[rowm][colm]=magcopy[rowm][colm]*(Math.cos((double)phasecopy[rowm][colm]*(Math.PI/180)));
						dataimg[rowm][colm]=magcopy[rowm][colm]*(Math.sin((double)phasecopy[rowm][colm]*(Math.PI/180)));
					}
				}
				for (int rowm = 0; rowm < picHeight; rowm++) {
					for (int colm = 0; colm <picWidth*2; colm=colm+2) {
						//col++;
						data2D_Updated[rowm][colm+1]=dataimg[rowm][colm/2];
						data2D_Updated[rowm][colm]=datareal[rowm][colm/2];
					}
				}

				// Apply Inverse FFT on the frequency matrix
				d.complexInverse(data2D, true);
				//d.complexInverse(data2D_Updated, true);
				filterFre=filterFre/(n*n*4);
				realfil=realfil/(n*n*4);
				imgfil=imgfil/(n*n*4);

				PrintWriter fftw = new PrintWriter(args[1].substring(0,args[1].indexOf("."))+"_" +n+ ".fft");
				fftw.println(picWidth + " " + picHeight+" "+n);


				for (int rowm = 0; rowm < picHeight; rowm++) {
					for (int colm = 0; colm <picWidth; colm++) {
						if((rowm<n && colm< (n)))
						{
							fftw.print(magcopy[rowm][colm]+" "+phasecopy[rowm][colm]+" ");
							//passing just the frequencies that are within the filter given:n
						}
						else if(rowm>picHeight-(n)-1 && colm < (n))
						{
							//fftw.print(data2D_Updated[rowm][colm]+" "+data2D_Updated[rowm][colm+1]+" ");
							fftw.print(magcopy[rowm][colm]+" "+phasecopy[rowm][colm]+" ");
							//passing just the frequencies that are within the filter given:n
						}
						else if(rowm<n && colm>(picWidth-(n)-1))
						{
							//	fftw.print(data2D_Updated[rowm][colm]+" "+data2D_Updated[rowm][colm+1]+" ");
							fftw.print(magcopy[rowm][colm]+" "+phasecopy[rowm][colm]+" ");
							//passing just the frequencies that are within the filter given:n	
						}
						else if (rowm>(picHeight-(n)-1) && colm>(picWidth-(n)-1))
						{
							//	fftw.print(data2D_Updated[rowm][colm]+" "+data2D_Updated[rowm][colm+1]+" ");
							fftw.print(magcopy[rowm][colm]+" "+phasecopy[rowm][colm]+" ");
							//passing just the frequencies that are within the filter given:n	
						}
						else
						{
							data2D_Updated[rowm][colm]=0;
							data2D_Updated[rowm][colm+1]=0;
						}
					}
					//fftw.println();
				}
				fftw.close();
				d.complexInverse(data2D_Updated, true);
				PrintWriter writero = new PrintWriter(args[1].substring(0,args[1].indexOf("."))+"_Orig" + ".pgm");
				writero.println("P2");
				writero.println("# Created by IrfanView");
				writero.println(picWidth + " " + picHeight);
				writero.println(255);
				for (int row = 0; row < picHeight; row++) {
					for (int col = 0; col < picWidth; col++) {
						writero.print((int)data2D[row][col]+"   ");
					}
					writero.println();
				}
				writero.close();

				PrintWriter writerop = new PrintWriter(args[1].substring(0,args[1].indexOf("."))+"_Const_"+n + ".pgm");
				writerop.println("P2");
				writerop.println("# Created by IrfanView");
				writerop.println(picWidth + " " + picHeight);
				writerop.println(255);
				for (int row = 0; row < picHeight; row++) {
					for (int col = 0; col < picWidth; col++) {
						writerop.print(Math.abs((int)data2D_Updated[row][col])+"   ");
						//System.out.print((int)data2D_Updated[row][col]+"   ");
					}
					writerop.println();
					//System.out.println();
				}
				writerop.close();

				PrintWriter writer = new PrintWriter(args[1].substring(0,args[1].indexOf("."))+"_Mag" + ".pgm");
				writer.println("P2");
				writer.println("# Created by IrfanView");
				writer.println(picWidth + " " + picHeight);
				writer.println(255);

				for (int row = 0; row < picHeight; row++) {
					for (int col = 0; col < picWidth; col++) {
						writer.print((int)mag[row][col]+"   ");
					}
					writer.println();
				}
				writer.close();

				PrintWriter writer2 = new PrintWriter(args[1].substring(0,args[1].indexOf("."))+"_Phase" + ".pgm");
				writer2.println("P2");
				writer2.println("# Created by IrfanView");
				writer2.println(picWidth + " " + picHeight);
				writer2.println(255);

				for (int row = 0; row < picHeight; row++) {
					for (int col = 0; col < picWidth; col++) {
						writer2.print(phase[row][col]+"  ");
					}
					writer2.println();
				}
				writer2.close();
				endTime=System.currentTimeMillis();
			}

			catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(choice==2)
		{
			startTime=System.currentTimeMillis();
			Scanner scanner;
			try {
				scanner = new Scanner(new File(filePath));
				int max = 0;
				picWidth = scanner.nextInt();
				picHeight = scanner.nextInt();
				int n=scanner.nextInt();
				if(n>picHeight && n>picWidth)
					throw new Exception();
				double[][] data2D = new double[picHeight][2*picWidth];
				for (int row = 0; row < 2*n; row++) {
					for (int col = 0; col < 2*2*n; col=col+2) {
						double magn=scanner.nextDouble();
						double phase=scanner.nextDouble();
						//if((row<n && col< (n*2))||(row>picHeight-(2*n)-1 && col < (2*n))||(row<n && col>(picWidth-(2*n)-1))||(row>(picHeight-(2*n)-1) && col>(picWidth-(2*n)-1)))
						data2D[row][col] = magn*(Math.cos((double)phase*(Math.PI/180)));
						data2D[row][col+1] = magn*(Math.sin((double)phase*(Math.PI/180)));
						//System.out.println(data2D[row][col]+"  "+data2D[row][col+1]);
					}
				}
				//				for(int i=n*2;i<2*2*n;i++)
				//			{
				//			int k=picWidth*2-i;
				int picW;
				int picH=picHeight-1;;
				for(int j=0;j<2*n;j++)
				{
					picW=2*picWidth-1;
					for(int i=(2*2*n)-1;i>=2*n;i--){
						data2D[j][picW--]=data2D[j][i];
						data2D[j][i]=0;
					}		
				}
				for(int i=2*n-1;i>=n;i--){
					for(int j=0;j<(2*picWidth);j++)
					{
						//System.out.println(i+"  "+j+"  "+(2*picWidth));
						data2D[picH][j]=data2D[i][j];
						data2D[i][j]=0;
					}
					picH--;
				}
				//for(int i=0;i<picHeight;i++){
				//for(int j=0;j<2*picWidth;j++)
				//System.out.print(data2D[i][j]+"  ");
				//System.out.println();
				//}
				//	}
				// Apply FFT on the given Image
				DoubleFFT_2D d = new DoubleFFT_2D(picHeight, picWidth);
				d.complexInverse(data2D, true);
				PrintWriter writero = new PrintWriter(args[1].substring(0,args[1].indexOf("."))+"_uncomp" + ".pgm");
				writero.println("P2");
				writero.println("# Created by IrfanView");
				writero.println(picWidth + " " + picHeight);
				writero.println(255);
				for (int row = 0; row < picHeight; row++) {
					for (int col = 0; col < picWidth; col++) {
						writero.print(Math.abs((int)data2D[row][col])+"   ");
						//writero.print((data2D[row][col])+"   "+data2D[row][col+1]);
					}
					writero.println();
				}
				writero.close();
				endTime=System.currentTimeMillis();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		System.out.println("Time for Execution: "+(endTime-startTime)+" millisecs");
	}
}
