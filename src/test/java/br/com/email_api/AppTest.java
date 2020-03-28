package br.com.email_api;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

/**
 * Unit test for simple App.
 */
public class AppTest {
	
//	// Modelo passando autenticacao direto na variavel
//	private String meuEmail = "seu_email@seu_provedor.com";
//	private String minhaSenha = "sua_senha";
	
	@org.junit.Test
	public void testeEmail() {
		
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
			
			// Modelo utilizando JOptionPane
			String userEmail = JOptionPane.showInputDialog("Digite seu email: ");
			String userSenha = JOptionPane.showInputDialog("Digite sua senha: ");
			
			// Neste exemplo, o Array so trabalha com um destinatario
			String userDestino = JOptionPane.showInputDialog("Digite o email de destino: ");
			
			Session session = Session.getInstance(properties, new Authenticator() {
				
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
//					return new PasswordAuthentication(meuEmail, minhaSenha);
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
					+ " para participar do teste da minha API de disparos de Email"
					+ " utilizando a linguagem Java.");
			
			// Envio do email
			Transport.send(message);
		} 
		
		catch (Exception e) {
			
			e.printStackTrace();
			System.out.println("Problema ao enviar.");
		}
	}
	
}
