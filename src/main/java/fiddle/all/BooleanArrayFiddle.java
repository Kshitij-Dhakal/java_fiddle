package fiddle.all;

public class BooleanArrayFiddle {
  public static boolean[][] transformArray(boolean[][] arr) {
    int rows = arr.length;
    int cols = arr[0].length;

    // Create a copy of the array to store the results
    boolean[][] result = new boolean[rows][cols];

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (arr[i][j]) {
          boolean aboveFalse = false;
          boolean leftFalse = false;

          // Check if any element above the current element is false
          for (int k = 0; k < i; k++) {
            if (!arr[k][j]) {
              aboveFalse = true;
              break;
            }
          }

          // Check if any element to the left of the current element is false
          for (int l = 0; l < j; l++) {
            if (!arr[i][l]) {
              leftFalse = true;
              break;
            }
          }

          result[i][j] = aboveFalse || leftFalse;
        } else {
          result[i][j] = false;
        }
      }
    }

    return result;
  }

  public static void main(String[] args) {
    boolean[][] arr = {
      {false, true, false},
      {true, true, true},
      {true, true, false}
    };

    boolean[][] transformedArr = transformArray(arr);

    for (boolean[] row : transformedArr) {
      for (boolean value : row) {
        System.out.print(value + " ");
      }
      System.out.println();
    }
  }
}
