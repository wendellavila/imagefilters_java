package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Main {
    
    static int m, n, grays;
    static String filename;
    
    static void generateOutputFile(int[][] mat) throws IOException {
        
        //criar nome unico usando data e hora
        String outputPath = filename.replace(".pgm", "");
        String timeLog = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime());
        outputPath = outputPath + "_" + timeLog + ".pgm";
        
        File file = new File(outputPath);
        FileWriter write = new FileWriter(file);
        PrintWriter print = new PrintWriter(write);
        
        print.println("P2");
        print.println("# Imagem " + filename + " modificada em " + timeLog);
        print.println(m + " " + n);
        print.println(grays);
        
        for(int i = 0; i < mat.length; i++){
            for(int j = 0; j < mat[0].length; j++){
                print.print(mat[i][j]);
                if(i < mat.length - 1){
                    print.print(" ");
                }
            }
            print.println();
        }
        print.close();
        System.out.println("Imagem \"" + outputPath + "\" criada com sucesso.");
    }
    
    static int[] find8Neighborhood(int[][] mat, int i, int j){
        
        //o pixel [i][j] sempre estará na ultima posicao do vetor
        int maxLin = mat.length;
        int maxCol = mat[0].length;
    
        if(i == 0 && j == 0){ //pixel está no canto superior esquerdo
            int[] neighborhood = { mat[i+1][j], mat[i+1][j+1], mat[i][j+1], 0, 0, 0, 0, 0, mat[i][j] };
            return neighborhood;
        }
        else if(i == 0 && j >= maxCol-1){ //pixel está no canto superior direito
            int[] neighborhood = { mat[i+1][j], mat[i+1][j-1], mat[i][j-1], 0, 0, 0, 0, 0, mat[i][j] };
            return neighborhood;
        }
        else if(i >= maxLin-1 && j == 0){ //pixel está no canto inferior esquerdo
            int[] neighborhood = { mat[i][j+1], mat[i-1][j], mat[i-1][j+1], 0, 0, 0, 0, 0, mat[i][j] };
            return neighborhood;
        }
        else if(i >= maxLin-1 && j >= maxCol-1){ //pixel está no canto inferior direito
            int[] neighborhood = { mat[i][j-1], mat[i-1][j-1], mat[i-1][j], 0, 0, 0, 0, 0, mat[i][j] };
            return neighborhood;
        }
        else if(i == 0 && j > 0 &&  j < maxCol-1){ //pixel está na linha superior exceto cantos
            int[] neighborhood = { mat[i+1][j], mat[i+1][j+1], mat[i][j+1], mat[i+1][j-1], mat[i][j-1], 0, 0, 0, mat[i][j] };
            return neighborhood;
        }
        else if(i >= maxLin-1 && j > 0 && j < maxCol-1){ //pixel está na linha inferior exceto cantos
            int[] neighborhood = { mat[i][j+1], mat[i-1][j], mat[i-1][j+1], mat[i][j-1], mat[i-1][j-1], 0, 0, 0, mat[i][j] };
            return neighborhood;
        }
        else if(i > 0 && i < maxLin-1 && j == 0){ //pixel está na linha mais à esquerda exceto cantos
            int[] neighborhood = { mat[i+1][j], mat[i+1][j+1], mat[i][j+1], mat[i-1][j], mat[i-1][j+1], 0, 0, 0, mat[i][j] };
            return neighborhood;
        }
        else if(i > 0 && i < maxLin-1 && j >= maxCol-1){ //pixel está na linha mais à direita exceto cantos
            int[] neighborhood = { mat[i+1][j], mat[i+1][j-1], mat[i][j-1], mat[i-1][j-1], mat[i-1][j], 0, 0, 0, mat[i][j] };
            return neighborhood;
        }
        else { //pixel esta no centro da imagem
            int[] neighborhood = { mat[i][j+1], mat[i][j-1], mat[i+1][j], mat[i-1][j], mat[i-1][j-1], mat[i-1][j+1], mat[i+1][j-1], mat[i+1][j+1], mat[i][j] };
            return neighborhood;
        }
    }
    
    static void passaBaixa(int[][] mat) throws IOException{
//        for(int i = 0; i < mat.length; i++){
//            for(int j = 0; j < mat[0].length; j++){
//                int[] neighborhood = find8Neighborhood(mat, i, j);
//                for(int k = 0; k < neighborhood.length; k++){
//                    //passa baixa
//                }
//            }
//        }
        generateOutputFile(mat);
    }
    static void passaAlta(int[][] mat) throws IOException{  
//        for(int i = 0; i < mat.length; i++){
//            for(int j = 0; j < mat[0].length; j++){
//                int[] neighborhood = find8Neighborhood(mat, i, j);
//                for(int k = 0; k < neighborhood.length; k++){
//                    //passa alta
//                }
//            }
//        }
        generateOutputFile(mat);
    }

    static void sobel(int[][] mat) throws IOException{
        generateOutputFile(mat);
    }
    static void prewitt(int[][] mat) throws IOException{
        generateOutputFile(mat);
    }
    static void roberts(int[][] mat) throws IOException{
        generateOutputFile(mat);
    }
    static void isotropico(int[][] mat) throws IOException{
        generateOutputFile(mat);
    }
    
    static void menu(int [][] originalMatrix) throws IOException{
	
        int option = 0;
        
        System.out.println("====================== Menu de Opcoes ======================\n");
        System.out.println("\t1 - Passa Baixa\n");
        System.out.println("\t2 - Passa Alta\n");
        System.out.println("\t3 - Sobel\n");
        System.out.println("\t4 - Prewitt\n");
        System.out.println("\t5 - Roberts\n");
        System.out.println("\t6 - Isotropico\n");
        System.out.println("\t7 - Sair\n");
        System.out.println("======== Entre com o numero de uma das opcoes acima ========\n");
        
        Scanner in = new Scanner(System.in);
        option = in.nextInt();
        while(option < 1 || option > 7){
            switch(option){
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

    public static void main(String[] args) throws IOException {
        
        Path path;
        
        if(args.length > 0){
            filename = args[0];
            path = Paths.get(filename);
        }
        else {
            filename = "mona_lisa.ascii.pgm";
            path = Paths.get(filename);
        }
        
        Scanner scan = new Scanner(path);
        scan.nextLine(); //P2
        scan.nextLine(); //comment
        m = scan.nextInt();
        n = scan.nextInt();
        grays = scan.nextInt();
        
        int [][] inputMatrix = new int[m][n];
        
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(scan.hasNextInt()){
                    inputMatrix[i][j] = scan.nextInt();
                }
            }
        }
        generateOutputFile(inputMatrix);
        //menu(inputMatrix);
    }
}
