package com.mertnamsal;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

public class Client {

	private JFrame frmUdpClient;
	private JTextField textField;
	private JEditorPane editorPane;
	private JScrollPane scrollPane;
	private JButton btnCarpma;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client window = new Client();
					window.frmUdpClient.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Client() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmUdpClient = new JFrame();
		frmUdpClient.setTitle("UDP Client");
		frmUdpClient.setBounds(100, 100, 768, 519);
		frmUdpClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmUdpClient.getContentPane().setLayout(null);

		textField = new JTextField();
		textField.setBounds(204, 177, 157, 20);
		frmUdpClient.getContentPane().add(textField);
		textField.setColumns(10);

		JButton btnTopla = new JButton("Topla");
		btnTopla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				sendMessage(5000);

			}
		});
		btnTopla.setBounds(399, 176, 89, 23);
		frmUdpClient.getContentPane().add(btnTopla);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(211, 256, 280, 160);
		frmUdpClient.getContentPane().add(scrollPane);
		
		editorPane = new JEditorPane();
		scrollPane.setViewportView(editorPane);
		
		btnCarpma = new JButton("Carpma");
		btnCarpma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMessage(5001); // Carpma servisinin portu
			}
		});
		btnCarpma.setBounds(517, 176, 89, 23);
		frmUdpClient.getContentPane().add(btnCarpma);
	}
	
	public void sendMessage(int port) {
		try {
			InetAddress address = InetAddress.getLocalHost();
			DatagramSocket datagramSocket = new DatagramSocket();
			String echoString;

			echoString = textField.getText();
			byte[] buffer = echoString.getBytes();
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
			datagramSocket.send(packet);

			// Server tarafından gelen echo mesajını alıp görüntüleyelim..
			byte[] buffer2 = new byte[50];
			packet = new DatagramPacket(buffer2, buffer2.length);
			datagramSocket.receive(packet);
			String receivedText = new String(buffer2, 0, packet.getLength());
			//System.out.println("Text received is : " + receivedText);
			editorPane.setText(editorPane.getText() + "\n" + receivedText);

		} catch (UnknownHostException e2) {
			e2.printStackTrace();
		} catch (SocketException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}

}
