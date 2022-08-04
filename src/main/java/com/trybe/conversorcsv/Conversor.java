package com.trybe.conversorcsv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * class conversor.
 */

public class Conversor {

  /**
   * Função utilizada apenas para validação da solução do desafio.
   *
   * @param args Não utilizado.
   * @throws IOException Caso ocorra algum problema ao ler os arquivos de entrada ou
   *                     gravar os arquivos de saída.
   */
  public static void main(String[] args) throws IOException {
    File pastaDeEntradas = new File("./entradas/");
    File pastaDeSaidas = new File("./saidas/");
    new Conversor().converterPasta(pastaDeEntradas, pastaDeSaidas);
  }

  /**
   * Converte todos os arquivos CSV da pasta de entradas. Os resultados são gerados
   * na pasta de saídas, deixando os arquivos originais inalterados.
   *
   * @param pastaDeEntradas Pasta contendo os arquivos CSV gerados pela página web.
   * @param pastaDeSaidas   Pasta em que serão colocados os arquivos gerados no formato
   *                        requerido pelo subsistema.
   * @throws IOException Caso ocorra algum problema ao ler os arquivos de entrada ou
   *                     gravar os arquivos de saída.
   */
  public void converterPasta(File pastaDeEntradas, File pastaDeSaidas) {
    // TODO: Implementar.

    for (File file : Objects.requireNonNull(pastaDeEntradas.listFiles())) {
      System.out.println("======================== FILE =================================");
      if (file.isFile()) {
        this.formataArquivo(file, pastaDeSaidas);
      }
    }

  }

  private void formataArquivo(File file, File pastaDeSaidas) {
    FileReader leitorArquivo = null;
    BufferedReader bufferedLeitor = null;
    ArrayList<String> listaDeLinhas = new ArrayList<>();
    ArrayList<String> novaListaDeLinhas = new ArrayList<>();

    try {
      leitorArquivo = new FileReader(file);
      bufferedLeitor = new BufferedReader(leitorArquivo);

      String conteudoLinha = bufferedLeitor.readLine();

      while (conteudoLinha != null) {
        listaDeLinhas.add(conteudoLinha);
        conteudoLinha = bufferedLeitor.readLine();
      }

      // formata cada linha
      for (String linha : listaDeLinhas) {
        if (!Objects.equals(linha, listaDeLinhas.get(0))) {
          String[] linhaSeparada = linha.split(",");
          String nome = linhaSeparada[0].toUpperCase();
          String oldDate = linhaSeparada[1].replace("/", "-");
          String date1 = oldDate.substring(0, 2);
          String date2 = oldDate.substring(3, 5);
          String date3 = oldDate.substring(6, 10);
          String newDate = date3 + "-" + date2 + "-" + date1;
          String cpf = linhaSeparada[3];
          String cpf1 = cpf.substring(0, 3);
          String cpf2 = cpf.substring(3, 6);
          String cpf3 = cpf.substring(6, 9);
          String cpf4 = cpf.substring(9, 11);
          String cpfFormatado = cpf1 + "." + cpf2 + "." + cpf3 + "-" + cpf4;
          String newLine = nome + "," + newDate + "," + linhaSeparada[2] + "," + cpfFormatado;
          novaListaDeLinhas.add(newLine);
        }
      }

      // add linhas formatadas e cria novo arquivo
      escreveNovoArquivo(file.getName(), novaListaDeLinhas, pastaDeSaidas);


    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      this.fecharLeitorArquivo(leitorArquivo, bufferedLeitor);
    }
  }

  private void fecharLeitorArquivo(FileReader fileReader, BufferedReader bufferedReader) {
    try {
      fileReader.close();
      bufferedReader.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void escreveNovoArquivo(
          String nomeDoArquivo,
          ArrayList<String> novaListaDeLinhas,
          File pastaDeSaidas) throws IOException {
    FileWriter escritorArquivo = null;
    BufferedWriter bufferedEscritor = null;
    String path = pastaDeSaidas + "/" + nomeDoArquivo;

    if (!pastaDeSaidas.exists()) {
      pastaDeSaidas.mkdirs();
    }


    try {
      escritorArquivo = new FileWriter(path);
      bufferedEscritor = new BufferedWriter(escritorArquivo);

      bufferedEscritor.write("Nome completo,Data de nascimento,Email,CPF\n");
      bufferedEscritor.flush();

      for (String linha : novaListaDeLinhas) {
        bufferedEscritor.write(linha + "\n");
        bufferedEscritor.flush();
      }


    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      this.fecharEscritaArquivo(escritorArquivo, bufferedEscritor);
    }
  }

  private void fecharEscritaArquivo(FileWriter escritorArquivo, BufferedWriter bufferedEscritor) {
    try {
      escritorArquivo.close();
      bufferedEscritor.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}