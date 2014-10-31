package eurekasan.unit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import twitter4j.Twitter;
import eurekasan.Bot;

public class SearchUnit extends Bot {
	public SearchUnit(Twitter twitter) {
		super(twitter);
	}

	@Override
	public StringBuilder method() {
		StringBuilder build = new StringBuilder();
		if (word.length() > 1) {
			build.append(word).append("を含むユニットは以下のとおりよ").append(BR);
			try (Stream<Path> stream = Files.find(Paths.get("units"), Integer.MAX_VALUE, (path, attr) -> path.toString().contains(word));) {
				stream.forEach(path -> {
					String pathName = path.getFileName().toString();
					build.append(pathName.substring(0, pathName.indexOf("."))).append(" ");
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			build.append("文字数が少なすぎるんじゃないかしら？");
		}
		return build;
	}
}
