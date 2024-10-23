import java.util.*;
public class PlayfairCipher {
    
    private static char[][] matrix = new char[5][5];
    private static final String ALPHABET = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
    
    private static String prepareKey(String key){
        StringBuilder result = new StringBuilder();
        key = key.toUpperCase().replaceAll("[^A-Z]", "");
        key = key.replace("J", "I");

        for(char c : key.toCharArray()){
            if(result.indexOf(String.valueOf(c))== -1){
                result.append(c);
            }
        }

        for(char c : ALPHABET.toCharArray()){
            if(result.indexOf(String.valueOf(c)) == -1){
                result.append(c);
            }
        }

        return result.toString();
    }

    private static void createMatrix(String key){
        String preparedKey = prepareKey(key);
        int index = 0 ;
        for(int row = 0 ; row<5;row++){
            for(int col = 0 ; col<5; col++){
                matrix[row][col] = preparedKey.charAt(index++);
            }
        }
    }

    private static String formatPlainText(String plaintext){
        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");
        plaintext = plaintext.replace("J", "I");
        StringBuilder formattedText = new StringBuilder();

        for(int i = 0 ; i< plaintext.length(); i++){
            char first = plaintext.charAt(i);
            char second = (i+1 < plaintext.length()) ? plaintext.charAt(i+1) : 'X';

            if(first == second){
                formattedText.append(first).append('X');
            }
            else{
                formattedText.append(first).append(second);
                i++;
            }   
        }
        if(formattedText.length() % 2 !=0){
            formattedText.append('X');
        }

        return formattedText.toString();
    }

    private static int[] findPosition(char c){
        for(int row = 0 ; row<5; row++){
            for(int col = 0 ; col < 5; col++){
                if(matrix[row][col] == c){
                    return new int[] {row,col};
                }
            }
        }
        return null;
    }

    private static String encryptPair(char a, char b ){
        int[] posA = findPosition(a);
        int[] posB = findPosition(b);

        if(posA[0] == posB[0]){
            return "" + matrix[posA[0]][(posA[1] + 1) % 5] + matrix[posB[0]][(posB[1]+1)%5];
        }
        else if(posA[1] == posB[1]){
            return "" + matrix[(posA[0] + 1 )% 5][posA[1]] + matrix[(posB[0] + 1)% 5 ][posB[1]];
        }
        else{
            return "" + matrix[posA[0]][posB[1]] + matrix[posB[0]][posA[1]];
        }
    }

    public static String encrypt(String key, String plaintext){
        createMatrix(key);
        String formattedText = formatPlainText(plaintext);
        StringBuilder encryptedText = new StringBuilder();

        for(int i = 0; i< formattedText.length(); i +=2){
            encryptedText.append(encryptPair(formattedText.charAt(i), formattedText.charAt(i+1)));
        }

        return encryptedText.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the key: ");
        String key = sc.nextLine();

        System.out.print("Enter plaintext: ");
        String plaintext = sc.nextLine();

        String encryptedText = encrypt(key, plaintext);
        System.out.println("Encrypted Text:" + encryptedText);

        sc.close();
    }
}
