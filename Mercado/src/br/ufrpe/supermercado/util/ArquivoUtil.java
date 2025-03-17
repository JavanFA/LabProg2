package br.ufrpe.supermercado.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ArquivoUtil {

    // Método para salvar dados em um arquivo
    public static void salvarEmArquivo(String caminhoArquivo, String dados) {
        try (FileWriter writer = new FileWriter(caminhoArquivo)) {
            writer.write(dados);
        } catch (IOException e) {
            System.err.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }

    // Método para carregar dados de um arquivo
    public static String carregarDeArquivo(String caminhoArquivo) {
        StringBuilder dados = new StringBuilder();
        try (Scanner scanner = new Scanner(new File(caminhoArquivo))) {
            while (scanner.hasNextLine()) {
                dados.append(scanner.nextLine()).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar arquivo: " + e.getMessage());
        }
        return dados.toString();
    }
}