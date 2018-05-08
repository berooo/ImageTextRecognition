package Utils;


public class WordRecognitionUtils {
	
	public static int getLeftPos(int[][] imageMatrix, int threshold) {
        int colNum = MatrixUtils.getColNum(imageMatrix);
        for (int col = 0; col < colNum; col++) {
            int[] colVector = MatrixUtils.getColVector(imageMatrix, col);
            for (int i = 0; i < colVector.length; i++) {
                if (colVector[i] < threshold) {
                    return col;
                }
            }
        }
        return -1;
    }

    public static int getRightPos(int[][] imageMatrix, int threshold) {
        int colNum = MatrixUtils.getColNum(imageMatrix);
        for (int col = colNum - 1; col >= 0; col--) {
            int[] colVector = MatrixUtils.getColVector(imageMatrix, col);
            for (int i = 0; i < colVector.length; i++) {
                if (colVector[i] < threshold) {
                    return col;
                }
            }
        }
        return -1;
    }

    public static int getTopPos(int[][] imageMatrix, int threshold) {
        int rowNum = MatrixUtils.getRowNum(imageMatrix);
        for (int row = 0; row < rowNum; row++) {
            int[] rowVector = MatrixUtils.getRowVector(imageMatrix, row);
            for (int i = 0; i < rowVector.length; i++) {
                if (rowVector[i] < threshold) {
                    return row;
                }
            }
        }
        return -1;
    }

    public static int getBottomPos(int[][] imageMatrix, int threshold) {
        int rowNum = MatrixUtils.getRowNum(imageMatrix);
        for (int row = rowNum - 1; row >= 0; row--) {
            int[] rowVector = MatrixUtils.getRowVector(imageMatrix, row);
            for (int i = 0; i < rowVector.length; i++) {
                if (rowVector[i] < threshold) {
                    return row;
                }
            }
        }
        return -1;
    }
    
}
