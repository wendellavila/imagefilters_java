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
import java.lang.Math;

public class Main {
    
    static int m, n, grays;
    static String filename;
    static int [][] originalMat;
    
    static void generateOutputFile(String filtername, int [][] newMat) throws IOException {
        
        //criar nome unico usando data e hora
        String outputPath = filename.replace(".pgm", "");
        outputPath = outputPath + "_" + filtername + ".pgm";
        
        File file = new File(outputPath);
        FileWriter write = new FileWriter(file);
        PrintWriter print = new PrintWriter(write);
        
        String timeLog = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime());
        print.println("P2");
        print.println("# Imagem " + filename + " modificada em " + timeLog);
        print.println(m + " " + n);
        print.println(grays);
        
        for(int i = 0; i < newMat.length; i++){
            for(int j = 0; j < newMat[0].length; j++){
                print.print(newMat[i][j]);
                if(i < newMat.length - 1){
                    print.print(" ");
                }
            }
            print.println();
        }
        print.close();
        System.out.println("Imagem \"" + outputPath + "\" criada com sucesso.");
    }
    
    static int[] find2x2Neighborhood(int i, int j){
        //encontrar vizinhanca 2x2 do roberts, inclusive de pixels na borda
        //retorna vetor 1x4 com o valor dos vizinhos, representando a originalMatriz 2x2 de vizinhança
        //valor -1 é atribuído se o pixel nao tem vizinho naquela posicao
        
        int maxLin = originalMat.length;
        int maxCol = originalMat[0].length;
        if(i >= maxLin-1 && j >= maxCol-1){ //pixel central está no canto inferior direito da imagem
            int[] neighborhood = { originalMat[i][j], -1, -1, -1 };
            return neighborhood;
        }
        else if(i < maxLin-1 && j >= maxCol-1){ //pixel central está na linha mais à direita da imagem, exceto canto inferior direito
            int[] neighborhood = { originalMat[i][j], -1, originalMat[i+1][j], -1 };
            return neighborhood;
        }
        else if(i >= maxLin-1 && j < maxCol-1){ //pixel central está na linha inferior da imagem, exceto canto inferior direito
            int[] neighborhood = { originalMat[i][j], originalMat[i][j+1], -1, -1 };
            return neighborhood;
        }
        else { //pixel central nao esta nas bordas da imagem
            int[] neighborhood = { originalMat[i][j], originalMat[i][j+1], originalMat[i+1][j], originalMat[i+1][j+1] };
            return neighborhood;
        }
    }
    
    static int[] find8Neighborhood(int i, int j){
        //encontrar vizinhanca 8, inclusive de pixels na borda
        //retorna vetor 1x9 com o valor dos vizinhos, representando a originalMatriz 3x3 de vizinhança
        //valor -1 é atribuído se o pixel nao tem vizinho naquela posicao
        
        int maxLin = originalMat.length;
        int maxCol = originalMat[0].length;
    
        if(i == 0 && j == 0){ //pixel central está no canto superior esquerdo da imagem
            
            int[] neighborhood = { -1, -1, -1, -1, originalMat[i][j], originalMat[i][j+1], -1, originalMat[i+1][j], originalMat[i+1][j+1] };
            return neighborhood;
        }
        else if(i == 0 && j >= maxCol-1){ //pixel central está no canto superior direito da imagem
            int[] neighborhood = { -1, -1, -1, originalMat[i][j-1], originalMat[i][j], -1, originalMat[i+1][j-1], originalMat[i+1][j], -1 };
            return neighborhood;
        }
        else if(i >= maxLin-1 && j == 0){ //pixel central está no canto inferior esquerdo da imagem
            int[] neighborhood = { -1, originalMat[i-1][j], originalMat[i-1][j+1], -1, originalMat[i][j], originalMat[i][j+1], -1, -1, -1 };
            return neighborhood;
        }
        else if(i >= maxLin-1 && j >= maxCol-1){ //pixel central está no canto inferior direito da imagem
            int[] neighborhood = { originalMat[i-1][j-1], originalMat[i-1][j], -1, originalMat[i][j-1], originalMat[i][j], -1, -1, -1, -1 };
            return neighborhood;
        }
        else if(i == 0 && j > 0 &&  j < maxCol-1){ //pixel central está na linha superior da imagem, exceto cantos
            int[] neighborhood = { -1, -1, -1, originalMat[i][j-1], originalMat[i][j], originalMat[i][j+1], originalMat[i+1][j-1], originalMat[i+1][j], originalMat[i+1][j+1] };
            return neighborhood;
        }
        else if(i >= maxLin-1 && j > 0 && j < maxCol-1){ //pixel central está na linha inferior da imagem, exceto cantos
            int[] neighborhood = { originalMat[i-1][j-1], originalMat[i-1][j], originalMat[i-1][j+1], originalMat[i][j-1], originalMat[i][j], originalMat[i][j+1], -1, -1, -1 };
            return neighborhood;
        }
        else if(i > 0 && i < maxLin-1 && j == 0){ //pixel central está na linha mais à esquerda da imagem, exceto cantos
            int[] neighborhood = { -1, originalMat[i-1][j], originalMat[i-1][j+1], -1, originalMat[i][j], originalMat[i][j+1], -1, originalMat[i+1][j], originalMat[i+1][j+1] };
            return neighborhood;
        }
        else if(i > 0 && i < maxLin-1 && j >= maxCol-1){ //pixel central está na linha mais à direita da imagem, exceto cantos
            int[] neighborhood = { originalMat[i-1][j-1], originalMat[i-1][j], -1, originalMat[i][j-1], originalMat[i][j], -1, originalMat[i+1][j-1], originalMat[i+1][j], -1 };
            return neighborhood;
        }
        else { //pixel central nao esta nas bordas da imagem
            int[] neighborhood = { originalMat[i-1][j-1], originalMat[i-1][j], originalMat[i-1][j+1], originalMat[i][j-1], originalMat[i][j], originalMat[i][j+1], originalMat[i+1][j-1], originalMat[i+1][j], originalMat[i+1][j+1] };
            return neighborhood;
        }
    }
    
    static void passaBaixa() throws IOException{
        int [][] newMat = new int[m][n];
        for(int i = 0; i < originalMat.length; i++){
            for(int j = 0; j < originalMat[0].length; j++){
                int[] neighborhood = find8Neighborhood(i, j);
                int soma = 0;
                for(int k = 0; k < neighborhood.length; k++){
                    if(neighborhood[k] != -1){
                        soma += neighborhood[k];
                    }
                }
                int newPixel = soma / 9;
                if(newPixel < 0){
                    newMat[i][j] = 0;
                }
                else if(newPixel > 255){
                    newMat[i][j] = 255;
                }
                else {
                    newMat[i][j] = newPixel;
                }
            }
        }
        System.out.println("Filtro aplicado com sucesso.");
        generateOutputFile("passaBaixa", newMat);
        menu();
    }
    static void passaAlta() throws IOException{
        int [][] newMat = new int[m][n];
        int [] passaAltaValues = { -1, -1, -1, -1, 8, -1, -1, -1, -1};
        for(int i = 0; i < originalMat.length; i++){
            for(int j = 0; j < originalMat[0].length; j++){
                int[] neighborhood = find8Neighborhood(i, j);
                int soma = 0;
                for(int k = 0; k < neighborhood.length; k++){
                    if(neighborhood[k] != -1){
                        soma += neighborhood[k] * passaAltaValues[k];
                    }
                }
                if(soma < 0){
                    newMat[i][j] = 0;
                }
                else if(soma > 255){
                    newMat[i][j] = 255;
                }
                else {
                    newMat[i][j] = soma;
                }
            }
        }
        System.out.println("Filtro aplicado com sucesso.");
        generateOutputFile("passaAlta", newMat);
        menu();
    }

    static void sobel() throws IOException{
        int [][] newMat = new int[m][n];
        int [] gradienteXValues = { -1, -2, -1, 0, 0, 0, 1, 2, 1};
        int [] gradienteYValues = { -1, 0, 1, -2, 0, 2, -1, 0, 1};
        for(int i = 0; i < originalMat.length; i++){
            for(int j = 0; j < originalMat[0].length; j++){
                int[] neighborhood = find8Neighborhood(i, j);
                int gradienteX = 0;
                int gradienteY = 0;
                for(int k = 0; k < neighborhood.length; k++){
                    if(neighborhood[k] != -1){
                        gradienteX += neighborhood[k] * gradienteXValues[k];
                        gradienteY += neighborhood[k] * gradienteYValues[k];
                    }
                }
                int gradiente = (int) Math.round(Math.sqrt(Math.pow(gradienteX, 2) + Math.pow(gradienteY, 2)));
                if(gradiente < 0){
                    newMat[i][j] = 0;
                }
                else if(gradiente > 255){
                    newMat[i][j] = 255;
                }
                else {
                    newMat[i][j] = gradiente;
                }
            }
        }
        System.out.println("Filtro aplicado com sucesso.");
        generateOutputFile("sobel", newMat);
        menu();
    }
    static void prewitt() throws IOException{
        int [][] newMat = new int[m][n];
        int [] gradienteXValues = { -1, -1, -1, 0, 0, 0, 1, 1, 1};
        int [] gradienteYValues = { -1, 0, 1, -1, 0, 1, -1, 0, 1};
        for(int i = 0; i < originalMat.length; i++){
            for(int j = 0; j < originalMat[0].length; j++){
                int[] neighborhood = find8Neighborhood(i, j);
                int gradienteX = 0;
                int gradienteY = 0;
                for(int k = 0; k < neighborhood.length; k++){
                    if(neighborhood[k] != -1){
                        gradienteX += neighborhood[k] * gradienteXValues[k];
                        gradienteY += neighborhood[k] * gradienteYValues[k];
                    }
                }
                int gradiente = (int) Math.round(Math.sqrt(Math.pow(gradienteX, 2) + Math.pow(gradienteY, 2)));
                if(gradiente < 0){
                    newMat[i][j] = 0;
                }
                else if(gradiente > 255){
                    newMat[i][j] = 255;
                }
                else {
                    newMat[i][j] = gradiente;
                }
            }
        }
        System.out.println("Filtro aplicado com sucesso.");
        generateOutputFile("prewitt", newMat);
        menu();
    }
    static void roberts() throws IOException{
        int [][] newMat = new int[m][n];
        int [] gradienteXValues = { -1, 0, 0, 1};
        int [] gradienteYValues = { 0, -1, 1, 0};
        for(int i = 0; i < originalMat.length; i++){
            for(int j = 0; j < originalMat[0].length; j++){
                int[] neighborhood = find2x2Neighborhood(i, j);
                int gradienteX = 0;
                int gradienteY = 0;
                for(int k = 0; k < neighborhood.length; k++){
                    if(neighborhood[k] != -1){
                        gradienteX += neighborhood[k] * gradienteXValues[k];
                        gradienteY += neighborhood[k] * gradienteYValues[k];
                    }
                }
                int gradiente = (int) Math.round(Math.sqrt(Math.pow(gradienteX, 2) + Math.pow(gradienteY, 2)));
                if(gradiente < 0){
                    newMat[i][j] = 0;
                }
                else if(gradiente > 255){
                    newMat[i][j] = 255;
                }
                else {
                    newMat[i][j] = gradiente;
                }
            }
        }
        System.out.println("Filtro aplicado com sucesso.");
        generateOutputFile("roberts", newMat);
        menu();
    }
    static void isotropico() throws IOException{
        int [][] newMat = new int[m][n];
        double [] gradienteXValues = { -1.0, 0.0, 1.0, -1*Math.sqrt(2), 0, Math.sqrt(2), -1.0, 0.0, 1.0};
        double [] gradienteYValues = { -1.0, -1*Math.sqrt(2), -1.0, 0, 0, 0, 1.0, Math.sqrt(2), 1.0};
        for(int i = 0; i < originalMat.length; i++){
            for(int j = 0; j < originalMat[0].length; j++){
                int[] neighborhood = find8Neighborhood(i, j);
                double gradienteX = 0.0;
                double gradienteY = 0.0;
                for(int k = 0; k < neighborhood.length; k++){
                    if(neighborhood[k] != -1){
                        gradienteX += neighborhood[k] * gradienteXValues[k];
                        gradienteY += neighborhood[k] * gradienteYValues[k];
                    }
                }
                int gradiente = (int) Math.round(Math.sqrt(Math.pow(gradienteX, 2) + Math.pow(gradienteY, 2)));
                if(gradiente < 0){
                    newMat[i][j] = 0;
                }
                else if(gradiente > 255){
                    newMat[i][j] = 255;
                }
                else {
                    newMat[i][j] = gradiente;
                }
            }
        }
        System.out.println("Filtro aplicado com sucesso.");
        generateOutputFile("isotropico", newMat);
        menu();
    }
    
    static void menu() throws IOException {
	
        int option = 0;
        Scanner in = new Scanner(System.in);
        
        System.out.println("====================== Menu de Opcoes ======================");
        System.out.println("\t1 - Passa Baixa");
        System.out.println("\t2 - Passa Alta");
        System.out.println("\t3 - Sobel");
        System.out.println("\t4 - Prewitt");
        System.out.println("\t5 - Roberts");
        System.out.println("\t6 - Isotropico");
        System.out.println("\t7 - Sair");
        System.out.println("======== Entre com o numero de uma das opcoes acima ========");
        
        while(option < 1 || option > 7){
            option = in.nextInt();
            switch(option){
                case 1:
                    passaBaixa();
                    break;
                case 2:
                    passaAlta();
                    break;
                case 3:
                    sobel();
                    break;
                case 4:
                    prewitt();
                    break;
                case 5:
                    roberts();
                break;
                case 6:
                    isotropico();
                break;
                case 7:
                break;
                default:
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
            filename = "lena.pgm";
            path = Paths.get(filename);
        }
        
        Scanner scan = new Scanner(path);
        scan.nextLine(); //P2
        scan.nextLine(); //comment
        m = scan.nextInt();
        n = scan.nextInt();
        grays = scan.nextInt();
        
        originalMat = new int[m][n];
        
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(scan.hasNextInt()){
                    originalMat[i][j] = scan.nextInt();
                }
            }
        }
        menu();
    }
}
