package cafein.file;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cafein.util.FileUtils;
import cafein.util.Result;

@RestController
public class APIFileController {
	private static final Logger logger = LoggerFactory.getLogger(APIFileController.class);
	private static final String filePath = "/Users/Thomas/toitoiImage";

	@Autowired
	private FileDAO filedao;

	@RequestMapping(value = "/api/post/file", method = RequestMethod.POST)
	public Result insertFile(@RequestParam(value = "imagefile", required = true) MultipartHttpServletRequest request) {
		logger.debug("hohohohoho1");
		Iterator<String> iterator = request.getFileNames();
		logger.debug("hohohohoho2");
		MultipartFile multipartFile = null;
		logger.debug("hohohohoho3");
		while (iterator.hasNext()) {
			logger.debug("hohohohoho4");
			multipartFile = request.getFile(iterator.next());
			if (multipartFile.isEmpty() == false) {
				logger.debug("hohohohoho5");
				logger.debug("filename : " + multipartFile.getOriginalFilename());

				File file = new File(filePath);
				if (file.exists() == false) {
					file.mkdirs();
				}

				String originalFileName = multipartFile.getOriginalFilename();
				String originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
				String storedFileName = FileUtils.getRandomString() + originalFileExtension;

				file = new File(filePath + storedFileName);

				try {
					multipartFile.transferTo(file);
				} catch (IOException e) {
					return Result.failed(e.toString());
				}

				ImageFile imageFile = new ImageFile(originalFileName, storedFileName);
				filedao.addFileInfo(imageFile);
				return Result.success(storedFileName);
			}
			logger.debug("hohohohoho6");
		}
		logger.debug("hohohohoho7");
		return Result.failed("multipartFile is null");
	}
}
