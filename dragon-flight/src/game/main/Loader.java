package game.main;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

// @YDH TODO 
// Loader 클래스는 삭제할 예정입니다.
// 추후 객체 생성 시 객체가 이미지 파일을 저장하도록 변경합니다.

public class Loader {

	public static void load() {
		try {
			Resource.backgroundMap = ImageIO.read(new File("image/backgroundMap.png"));
			Resource.player = ImageIO.read(new File("image/player.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
