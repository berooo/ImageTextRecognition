package Utils;


public class MatrixUtils {
	
	    //图像向x轴做投影后的数组
		public static int[] xpro(int[][] matrix,int width,int height) {
			
			int[] xpro=new int[width];
			for(int i=0;i<width;i++) {
				for(int j=0;j<height;j++) {
					if(matrix[i][j]<255) {
						xpro[i]++;
					}
				}
			}
			return xpro;
			
		}
		//图像向y轴做投影后的数组
		public static int[] ypro(int[][] matrix,int width,int height) {
			int[] ypro=new int[height];
			
			for(int i=0;i<width;i++) {
				for(int j=0;j<height;j++) {
					if(matrix[i][j]<255) {
						ypro[j]++;
					}
				}
			}
			return ypro;
			
		}
		
	 public static int[] getColVector(int[][] matrix, int row) {
	        return matrix[row];
	    }

	 public static int[] getRowVector(int[][] matrix, int col) {
	        int[] colVector = new int[matrix.length];
	        for (int row = 0; row < matrix.length; row++) {
	            colVector[row] = matrix[row][col];
	        }
	        return colVector;
	    }
	    
	 public static int getColNum(int[][] matrix) {
	        return matrix.length;
	    }

	 public static int getRowNum(int[][] matrix) {
	        return matrix[0].length;
	    }
	
	public static double[][] convertMatrix(int[][] matrix) {
        int rowNum = matrix.length;
        int colNum = matrix[0].length;
        double[][] resMatrix = new double[rowNum][colNum];
        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < colNum; col++) {
                resMatrix[row][col] = matrix[row][col];
            }
        }
        return resMatrix;
    }
	
	 public static int[][] getSubMatrix(int[][] matrix, int startRow, int startCol, int rowNum, int colNum) {
	        int[][] resMatrix = new int[rowNum][colNum];
	        for (int i = 0; i < rowNum; i++) {
	            for (int j = 0; j < colNum; j++) {
	                resMatrix[i][j] = matrix[i + startRow][j + startCol];
	            }
	        }
	        return resMatrix;
	 }
}
