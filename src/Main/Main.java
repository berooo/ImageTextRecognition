package Main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Utils.ImageUtils;
import Utils.MatrixUtils;
import Utils.WordRecognitionUtils;

public class Main {
	

   private final static int GRAY_THRESHOLD = 100;
	
	public static void main(String[] args) throws IOException {
//		double[][] doubleMatrix = null;
		
//		ImageUtils.discardAlpha("D:/1.png", "D:/win.png");
//		int[][] intMatrix=ImageUtils.binaryImage("D:/win.png", "D:/winwin.png");
		int[][] intMatrix=ImageUtils.getGray("D:/winwin.png");

	//	doubleMatrix=MatrixUtils.convertMatrix(intMatrix);
//		 int leftPos = WordRecognitionUtils.getLeftPos(intMatrix, GRAY_THRESHOLD);
//         int rightPos = WordRecognitionUtils.getRightPos(intMatrix, GRAY_THRESHOLD);
//         int topPos = WordRecognitionUtils.getTopPos(intMatrix, GRAY_THRESHOLD);
//         int bottomPos = WordRecognitionUtils.getBottomPos(intMatrix, GRAY_THRESHOLD);
//         int width = rightPos - leftPos + 1;
//         int height = bottomPos - topPos + 1;
//         intMatrix = MatrixUtils.getSubMatrix(intMatrix, leftPos, topPos, width, height);
		intMatrix=ImageUtils.contract(intMatrix);
         //对图片进行行分割
         List<int[][]> ls=ImageUtils.lineSegmentation(intMatrix, intMatrix.length,intMatrix[0].length);
         
         List<int[][]> ils=ImageUtils.wordSegmentation(ls);
         for(int i=0;i<15;i++) {
        	 int[][] aa=ils.get(i);
        	 String path="D:/"+i+".png";
        	 ImageUtils.toImg(aa,path);
         }
   //      System.out.println(lineNum);
	}
}
