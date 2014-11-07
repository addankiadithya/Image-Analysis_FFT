Step-by-step guide for execution using Command Prompt:
======================================================
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


Description:
------------
1. You can Deploy our project by extracting all the files into a folder
2. Please ensure to include JTransforms.jar file into the class path for compiling and execution, syntax given below

Compile:
D:\ABBS_FP>javac -cp ".;JTransforms.jar" FFT.java

Execute:
D:\ABBS_FP>java -cp ".;JTransforms.jar" FFT [-arguments]

Execution:
----------
Modules:
  1. Reads the input PGM(Portable Gray Map-P2) file and converts the image into magnitude & phase forms along with the fft file for compression
		
		Execution:
		D:\ABBS_FP>java -cp ".;JTransforms.jar" FFT 1 coins_p.pgm filtervalue
		
  2. Reverses what is done in module 1 from the fft file of module 1
		(Saves image as filename_filtervalue.pgm)
		
		Execution:
		D:\ABBS_FP>java -cp ".;JTransforms.jar" FFT 2 coins_p_k.fft
		