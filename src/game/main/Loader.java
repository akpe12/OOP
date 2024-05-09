package game.main;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

// TODO 
// Loader 클래스는 삭제할 예정입니다.
// 추후 객체 생성 시 객체가 이미지 파일을 SpriteSheet으로 저장하도록 변경합니다.
// 예시) player = new Player(new SpriteSheet("entity/wizardPlayer.png", 64, 64), ...);

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
