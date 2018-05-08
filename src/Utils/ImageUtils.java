package Utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageUtils {
	
	private final static int GRAY_THRESHOLD = 100;
	
	//把图片加载到内存缓冲区之中
	public static BufferedImage loadImage(String filepath)throws IOException{
	
		File imageFile=new File(filepath);
		return ImageIO.read(imageFile);
	
	}
	
	
	//将图片二值化
	public static int[][] binaryImage(String path,String newpath) throws IOException{  
	    File file = new File(path);  
	    BufferedImage image = ImageIO.read(file);  
	    file.delete();
	      
	    int width = image.getWidth();  
	    int height = image.getHeight();  
	    int[][] imageData=new int[width][height];  
	    BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);//重点，技巧在这个参数BufferedImage.TYPE_BYTE_BINARY  
	    for(int i= 0 ; i < width ; i++){  
	        for(int j = 0 ; j < height; j++){  
	        int rgb = image.getRGB(i, j);  
	        grayImage.setRGB(i, j, rgb);  
	        imageData[i][j]=rgb;
	        }  
	    }  
	      
	    File newFile = new File(newpath);  
//	    ImageIO.write(grayImage, "png", newFile);
	    return imageData;
	 }
	
	//getRGB是沿height方向扫描图片的
	public static int[][] getGray(String path) throws IOException {
		File file = new File(path);
		BufferedImage bufferedImage=ImageIO.read(file); 
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int[][] imageData = new int[width][height];
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                int rgb = bufferedImage.getRGB(w, h);
                int grey = (int) (0.3 * ((rgb & 0xff0000) >> 16) + 0.59 * ((rgb & 0xff00) >> 8) + 0.11 * ((rgb & 0xff)));
                imageData[w][h] = grey;
            }
        }
  //      toImg(imageData);
        return imageData;
    }
	 //去掉透明度
	 public static void discardAlpha(String oldpath,String newpath) {

		   File file = new File(oldpath);
		   InputStream is;
		   try {
		    is = new FileInputStream(file);//如果是MultipartFile类型，那么自身也有转换成流的方法：is = file.getInputStream();
		    BufferedImage bi=ImageIO.read(is);
		    Image image=(Image)bi;
		    ImageIcon imageIcon = new ImageIcon(image);
		    BufferedImage bufferedImage = new BufferedImage(imageIcon
		      .getIconWidth(), imageIcon.getIconHeight(),
		      BufferedImage.TYPE_INT_RGB);
		    
		    
		    Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
		    g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon
		      .getImageObserver());
		    int alpha = 0;
		    for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage
		      .getHeight(); j1++) {
		     for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage
		       .getWidth(); j2++) {
		      int rgb = bufferedImage.getRGB(j2, j1);
		    
		      int a=(rgb & 0xff000000 ) >> 24 ;
		      int R =(rgb & 0x00ff0000 ) >> 16 ;
		      int G= (rgb & 0x0000ff00 ) >> 8 ;
		      int B= (rgb & 0xff );
		      
		      if(R==0&&G==0&&B==0) {
		    	  rgb=rgb|0x00ffffff;
		      }

		      bufferedImage.setRGB(j2, j1, rgb);
		      
		      
		     }
		    }
           
		    g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());
		    ImageIO.write(bufferedImage, "png", new File(newpath));//直接输出文件

		   } catch (Exception e) {
		       e.printStackTrace();
		   }
	 }
	
	 public static List<int[][]> lineSegmentation(int[][] matrix,int width,int height){
		 int[] ypro=MatrixUtils.ypro(matrix, width, height);
         
         List<int[][]> ls=new ArrayList<int[][]>();

         int lineNum=1;
         int topPos=0,bottomPos=0,leftPos=0;
         for(int i=0;i<ypro.length;i++) {
        	 
        	 if((i<ypro.length-1)) {
        		 if(ypro[i]!=0&&ypro[i+1]==0) {
        			 
        			 bottomPos=i;
        			 if(topPos<bottomPos) {
        			//	 int[][] a=new int[bottomPos-topPos][intMatrix.length];
        				int[][] a=MatrixUtils.getSubMatrix(matrix, leftPos, topPos, width, bottomPos-topPos+1);
        				ls.add(a);
        			 }
        		 }
        			 
        		 if(ypro[i]==0&&ypro[i+1]!=0) {
        			 topPos=i+1;
        		 }
        		 
        		 
        	 }
        	 
         }
         int[][] a=MatrixUtils.getSubMatrix(matrix, leftPos, topPos, width, matrix[0].length-topPos);
         ls.add(a);
		return ls;
		 
	 }
	 
	 public static List<int[][]> wordSegmentation(List<int[][]> ls) {
		 
		 List<int[][]> result=new ArrayList<int[][]>();
		 
		 for(int i=0;i<ls.size();i++) {
			 int leftPos=0,rightPos=0;
			 int[][] matrix=ls.get(i);
			 int height=matrix[0].length;
			 matrix=contract(matrix);
			 int[] xpro=MatrixUtils.xpro(matrix, matrix.length, matrix[0].length);
			 for(int j=0;j<xpro.length;j++) {
				 
				 rightPos=j;
				 if(rightPos-leftPos<height) {
					 if(j<xpro.length-1&&xpro[j]!=0&&xpro[j+1]==0) {
						 if(leftPos<rightPos) {
							 int[][] a=MatrixUtils.getSubMatrix(matrix, leftPos, 0, rightPos-leftPos+1, matrix[0].length);
							 result.add(a);
							 leftPos=rightPos;
						 }
					 }
				 }else {
					 int[][] a=MatrixUtils.getSubMatrix(matrix, leftPos, 0, rightPos-leftPos+1, matrix[0].length);
					 result.add(a);
					 leftPos=rightPos;
				 }
				
				 
				 if(j<xpro.length-1&&xpro[j]==0&&xpro[j+1]!=0) {
					 leftPos=j+1;
				 }
			 }
			 
			 if(rightPos>leftPos) {
			 int[][] a=MatrixUtils.getSubMatrix(matrix, leftPos, 0, rightPos-leftPos, matrix[0].length);
			 result.add(a);
			 }
		 }
		 return result;
		 
		 
	 }
	 
	 public static int[][] contract(int[][] intMatrix){
		 int leftPos = WordRecognitionUtils.getLeftPos(intMatrix, GRAY_THRESHOLD);
         int rightPos = WordRecognitionUtils.getRightPos(intMatrix, GRAY_THRESHOLD);
         int topPos = WordRecognitionUtils.getTopPos(intMatrix, GRAY_THRESHOLD);
         int bottomPos = WordRecognitionUtils.getBottomPos(intMatrix, GRAY_THRESHOLD);
         int width = rightPos - leftPos + 1;
         int height = bottomPos - topPos + 1;
         return intMatrix = MatrixUtils.getSubMatrix(intMatrix, leftPos, topPos, width, height);
	 }
	 public static void toImg(int[][] b,String path) throws IOException {
		 
		 int width=b.length;
		 int height=b[0].length;
		 
		 BufferedImage bufferedImage = new BufferedImage(width,height,
			      BufferedImage.TYPE_INT_RGB);
//		 Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
		
		 for(int i=0;i<height;i++) {
			 for(int j=0;j<width;j++) {
				 bufferedImage.setRGB(j,i, (int) b[j][i]);
			 }
		 }
		 
		 ImageIO.write(bufferedImage, "png", new File(path));//直接输出文件
	 }
}
