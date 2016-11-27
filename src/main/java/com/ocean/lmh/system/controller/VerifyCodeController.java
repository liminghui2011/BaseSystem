package com.ocean.lmh.system.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ocean.lmh.base.constant.AppConstant;

/**
 * 生成验证码的控制类
 * @author liminghui
 *
 */
@Controller
@RequestMapping ( value = "/system" )
public class VerifyCodeController {

	public Color getRandColor ( int fc , int bc ) {
		Random random = new Random ( );
		if ( fc > 255 )
			fc = 255;
		if ( bc > 255 )
			bc = 255;
		int r = fc + random.nextInt ( bc - fc );
		int g = fc + random.nextInt ( bc - fc );
		int b = fc + random.nextInt ( bc - fc );
		return new Color ( r , g , b );
	}

	@RequestMapping ( value = "/verifyCode" )
	public void genereate ( HttpServletRequest request , HttpServletResponse response ) throws IOException {
		int width = 60 , height = 20;
		BufferedImage image = new BufferedImage ( width , height , BufferedImage.TYPE_INT_RGB );

		Graphics g = image.getGraphics ( );

		Random random = new Random ( );

		g.setColor ( getRandColor ( 200 , 250 ) );
		g.fillRect ( 0 , 0 , width , height );

		g.setFont ( new Font ( "Times New Roman" , Font.PLAIN , 18 ) );

		// g.setColor(new Color());
		// g.drawRect(0,0,width-1,height-1);

		g.setColor ( getRandColor ( 160 , 200 ) );
		for ( int i = 0 ; i < 10 ; i++ ){
			int x = random.nextInt ( width );
			int y = random.nextInt ( height );
			int xl = random.nextInt ( 12 );
			int yl = random.nextInt ( 12 );
			g.drawLine ( x , y , x + xl , y + yl );
		}

		String sRand = "";
		for ( int i = 0 ; i < 4 ; i++ ){
			String rand = String.valueOf ( random.nextInt ( 10 ) );
			sRand += rand;
			g.setColor ( new Color ( 20 + random.nextInt ( 110 ) , 20 + random.nextInt ( 110 ) , 20 + random.nextInt ( 110 ) ) );//
			g.drawString ( rand , 13 * i + 6 , 16 );
		}

		request.getSession ( ).setAttribute ( AppConstant.VERIFY_CODE , sRand );

		g.dispose ( );

		response.setHeader ( "Pragma" , "No-cache" );
		response.setHeader ( "Cache-Control" , "no-cache" );
		response.setDateHeader ( "Expires" , 0 );

		ImageIO.write ( image , "JPEG" , response.getOutputStream ( ) );
	}
}
