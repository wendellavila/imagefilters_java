package main;

import java.io.File;

public class Main {
    
    static int[] find8Neighborhood(int[][] mat, int i, int j){
        int maxLin = mat.length;
        int maxCol = mat[0].length;
        
    
        if(i == 0 && j == 0){ //canto superior esquerdo
            int[] neighborhood = { mat[i+1][j], mat[i+1][j+1], mat[i][j+1], 0, 0, 0, 0, 0, mat[i][j] };
            return neighborhood;
        }
        else if(i == 0 && j >= maxCol-1){ //canto superior direito
            int[] neighborhood = { mat[i+1][j], mat[i+1][j-1], mat[i][j-1], 0, 0, 0, 0, 0, mat[i][j] };
            return neighborhood;
        }
        else if(i >= maxLin-1 && j == 0){ //canto inferior esquerdo
            int[] neighborhood = { mat[i][j+1], mat[i-1][j], mat[i-1][j+1], 0, 0, 0, 0, 0, mat[i][j] };
            return neighborhood;
        }
        else if(i >= maxLin-1 && j >= maxCol-1){ //canto inferior direito
            int[] neighborhood = { mat[i][j-1], mat[i-1][j-1], mat[i-1][j], 0, 0, 0, 0, 0, mat[i][j] };
            return neighborhood;
        }
        else if(i == 0 && j > 0 &&  j < maxCol-1){ //linha superior exceto cantos
            int[] neighborhood = { mat[i+1][j], mat[i+1][j+1], mat[i][j+1], mat[i+1][j-1], mat[i][j-1], 0, 0, 0, mat[i][j] };
            return neighborhood;
        }
        else if(i >= maxLin-1 && j > 0 && j < maxCol-1){ //linha inferior exceto cantos
            int[] neighborhood = { mat[i][j+1], mat[i-1][j], mat[i-1][j+1], mat[i][j-1], mat[i-1][j-1], 0, 0, 0, mat[i][j] };
            return neighborhood;
        }
        else if(i > 0 && i < maxLin-1 && j == 0){ //linha esquerda exceto cantos
            int[] neighborhood = { mat[i+1][j], mat[i+1][j+1], mat[i][j+1], mat[i-1][j], mat[i-1][j+1], 0, 0, 0, mat[i][j] };
            return neighborhood;
        }
        else if(i > 0 && i < maxLin-1 && j >= maxCol-1){ //linha direita exceto cantos
            int[] neighborhood = { mat[i+1][j], mat[i+1][j-1], mat[i][j-1], mat[i-1][j-1], mat[i-1][j], 0, 0, 0, mat[i][j] };
            return neighborhood;
        }
        else { //pixels centrais
            int[] neighborhood = { mat[i][j+1], mat[i][j-1], mat[i+1][j], mat[i-1][j], mat[i-1][j-1], mat[i-1][j+1], mat[i+1][j-1], mat[i+1][j+1], mat[i][j] };
            return neighborhood;
        }
    }
    
    static void passaBaixa(int[][] mat){
        for(int i = 0; i < mat.length; i++){
            for(int j = 0; j < mat[0].length; j++){
                int[] neighborhood = find8Neighborhood(mat, i, j);
                for(int k = 0; k < neighborhood.length; k++){
                    //passa baixa
                }
            }
        }
    }
    static void passaAlta(int[][] mat){  
        for(int i = 0; i < mat.length; i++){
            for(int j = 0; j < mat[0].length; j++){
                int[] neighborhood = find8Neighborhood(mat, i, j);
                for(int k = 0; k < neighborhood.length; k++){
                    //passa alta
                }
            }
        }
    }

    static void sobel(int[][] mat){}
    static void prewitt(int[][] mat){}
    static void roberts(int[][] mat){}
    static void isotropico(int[][] mat){}
    
    static void menu(int [][] originalMatrix){
	
        int option;

        option = 0;
        
        System.out.println("====================== Menu de Opcoes ======================\n\n");
        System.out.println("\t1 - Passa Baixa\n");
        System.out.println("\t2 - Passa Alta\n");
        System.out.println("\t3 - Sobel\n");
        System.out.println("\t4 - Prewitt\n");
        System.out.println("\t5 - Roberts\n");
        System.out.println("\t6 - Isotropico\n");
        System.out.println("\t7 - Sair\n\n");
        System.out.println("======== Entre com o numero de uma das opcoes acima ========\n\n");

        while(option < 1 || option > 7){
            //scanf(" %d", &option);
            switch( option ){
                case 1:
                    passaBaixa(originalMatrix);
                    break;
                case 2:
                    passaAlta(originalMatrix);
                    break;
                case 3:
                    sobel(originalMatrix);
                    break;
                case 4:
                    prewitt(originalMatrix);
                    break;
                case 5:
                    roberts(originalMatrix);
                break;
                case 6:
                    isotropico(originalMatrix);
                break;
                case 7:
                break;
                default:
                    System.out.println("\n\tEntrada invalida. Tente novamente.\n\n");
                break;
            } //switch
        } //while
    }

    public static void main(String[] args) {
        File file = new File("mona_lisa.ascii.pgm");
        int [][] inputMatrix = new int[1][1];
        menu(inputMatrix);
    }
}
