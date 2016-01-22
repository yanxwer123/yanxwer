package com.kld.app.view.monitor;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Task {
	private JPanel panel;
	private JPanel content;
	public JPanel getContent() {
		return content;
	}
	public void setContent(JPanel content) {
		this.content = content;
	}

	private ScheduledExecutorService service = Executors
			.newSingleThreadScheduledExecutor();
	public Task(JPanel panel,JPanel content){
		this.panel = panel;
		this.content = content;
	}
	public void checking() {
		Runnable runnable = new Runnable() {
			public void run() {
				running();
			}
		};
		service.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
	}
	public void running() {
		panel.remove(content);
//		ImageIcon jcz = Common.createImageIcon(this.getClass(),"circle_12.gif");
//		content = new BackgroundPanel(jcz.getImage(),getInfo());
//		content.setBounds(664, 2, 140, 140);
//		content.setVisible(true);
		panel.add(content);
		panel.repaint();
	}
	
	private String getInfo() {
		int n = (int)(Math.random()*100);
		//System.out.println(n);
		return "  "+n+"%";
	}
	public void pause(){
		service.shutdownNow();
	}
}