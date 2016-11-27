package com.ocean.lmh.system.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ocean.lmh.base.util.IOSystem;
import com.ocean.lmh.system.service.SeqService;


@Controller
@RequestMapping ( value = "/test" )
public class TestSeqController {
	
	@Resource(name="seqService")
	private SeqService seqService;
	
	@RequestMapping ( value = "/seq" )
	public void index ( String mid , HttpSession session ) throws InterruptedException {
		
		GetSeq seq1 = new GetSeq(seqService, "TASK-1 ");
		GetSeq seq2 = new GetSeq(seqService, "TASK-2 ");
		GetSeq seq3 = new GetSeq(seqService, "TASK-3 ");
		seq1.start();
		seq2.start();
		seq3.start();
		
		seq1.join();
		seq2.join();
		seq3.join();
		
		IOSystem.writeTo(seq1.sb.toString(), "d:/req1.txt");
		IOSystem.writeTo(seq2.sb.toString(), "d:/req2.txt");
		IOSystem.writeTo(seq3.sb.toString(), "d:/req3.txt");
	}
	
	private class GetSeq extends Thread{
		
		public StringBuilder sb;
		
		public GetSeq(SeqService seqService, String threadId)
		{
			sb = new StringBuilder();
		}
		
		public void run()
		{
			int i = 0;
			while(i < 100)
			{
				i++;
			}
		}
	}
}
