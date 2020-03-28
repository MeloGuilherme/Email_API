package br.com.email_api;

/**
 * 
 * @author Guilherme Melo (https://github.com/MeloGuilherme)
 * 
 * */

import java.util.Properties;
import java.util.Scanner;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

public class App {

	
	/*
	 * Este projeto serve para envio de email, utilizando a linguagem Java e o servico
	 * de email Gmail (Google). Para utilizar outros servidores como Outlook (Microsoft)
	 * ou outro de preferencia, e preciso alterar o metodo enviarEmail(String, String, String),
	 * adiconando os valores referentes ao servico SMTP do provedor escolhido.
	 * 
	 * Temos como exemplo de testes dois metodos: console() e caixaDialogo();
	 * 
	 * console(): utiliza valores digitados pelo usuario no console da IDE;
	 * caixaDialogo: utiliza o JOptionPane para receber os valores;
	 * 
	 * Para utilizar os metodos, precisamos descomentar o metodo principal (main) da
	 * aplicacao, e escolher qual modelo de entrada de dados usar, para realizar o envio.
	 * 
	 * Este modelo de envio tambem pode ser utilizado em paginas web, desde que o programador
	 * faca as devidas referencias e adaptacoes corretamente, para poder enviar.
	 * 
	 * Ressalto tambem que eh possivel mandar o email para mais de um usuario de uma vez,
	 * ja que a referencia Adress do metodo de envio eh um Array, o que permite enviar para um
	 * unico destinatario ou para mais de um.
	 * 
	 * No metodo de envio tambem podemos alterar o assunto do email (setSubject) e tambem o conteudo
	 * do email (setText), adicionando os valores desejados. Uma ideia de uso do metodo em uma
	 * aplicacao maior, eh transformar estes valores em campos para o usuario digitar o assunto
	 * e o texto, depois selecionar os destinatarios e enviar.
	 * 
	 * No pacote de testes do projeto, temos uma Classe com o metodo de envio, utilizando a
	 * anotacao de testes do JUnit 4.12, para fazer as alteracoes necessarias e testar, antes da 
	 * implementacao na aplicacao principal.
	 * 
	 * Obs.: Se o email nao aparecer na caixa de entrada do Email do destinatario, eh preciso
	 * olhar na caixa de Spam ou no lixo eletrônico, pois a seguranca do provador de email pode
	 * interpretar como ameaca e nap entragar diretamente a mensagem.
	 * 
	 * Bom uso!
	 * 
	 * */
	
	private String meuEmail;
	private String minhaSenha;
	private String meuDestino;

//	public static void main(String[] args) {
//		
//		App app = new App();
//		
//		app.caixaDialogo();
//		app.console();
//
//	}
	
	public void console() {

		Scanner input = new Scanner(System.in);

		System.out.println("Digite o seu email: ");
		meuEmail = input.nextLine();

		System.out.println("Digite sua senha: ");
		minhaSenha = input.nextLine();

		System.out.println("Digite o email de destino: ");
		meuDestino = input.nextLine();

		enviarEmail(meuEmail, minhaSenha, meuDestino);

		input.close();

	}

	public void caixaDialogo() {

		meuEmail = JOptionPane.showInputDialog("Digite o seu email:");

		minhaSenha = JOptionPane.showInputDialog("Digite sua senha");

		meuDestino = JOptionPane.showInputDialog("Digite o email de destino");

		enviarEmail(meuEmail, minhaSenha, meuDestino);
	}

	public void enviarEmail(String userEmail, String userSenha, String userDestino) {

		try {

			Properties properties = new Properties();

			// Autorizacao de envio
			properties.put("mail.smtp.auth", "true");

			// Autenticacao da conta
			properties.put("mail.smtp.starttls", "true");

			// Servidor do Gmail - Google
			properties.put("mail.smtp.host", "smtp.gmail.com");

			// Porta do servidor Gmail - Google
			properties.put("mail.smtp.port", "465");

			// Especificando a porta de conexao para o Socket
			properties.put("mail.smtp.socketFactory.port", "465");

			// Classe Socket de conexao ao SMTP
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

			Session session = Session.getInstance(properties, new Authenticator() {

				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userEmail, userSenha);
				}
			});

			// Array para permitir um ou varios destinatarios
			Address[] toUser = InternetAddress.parse(userDestino);

			Message message = new MimeMessage(session);

			// Email do usuario que vai enviar
			message.setFrom(new InternetAddress(userEmail));

			// Email dos destinatarios
			message.setRecipients(Message.RecipientType.TO, toUser);

			// Assunto do email
			message.setSubject("Testando minha API de Email");

			// Corpo do email (texto)
			message.setText("Se você recebeu essa mensagem, significa que você foi escolhido"
					+ " para participar do teste da minha API de disparos de Email" + " utilizando a linguagem Java.");

			// Envio do email
			Transport.send(message);
			
			System.out.println("\n" + "Email enviado com sucesso!");
		}

		catch (Exception e) {

			e.printStackTrace();
		}
	}
}
