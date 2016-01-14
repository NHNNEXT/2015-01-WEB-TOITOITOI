package cafein.file;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cafein.repository.ImageRepository;
import cafein.util.ImageFileUtils;
import cafein.util.Result;

@RestController
public class APIFileController {
	@Autowired
	private FileDAO filedao;
	@Autowired
	private ImageRepository imageDao;
	
	private static final Logger logger = LoggerFactory.getLogger(APIFileController.class);
//	private static final String filePath = "/root/images/";
	private static final String filePath = "/Users/heesu/toitoiImage/";
	
	
	public String insertFile(MultipartFile multipartFile) {

		logger.debug("filename : " + multipartFile.getOriginalFilename());
		logger.debug("filePath:" + filePath);
		File file = new File(filePath);
		if (file.exists() == false) {
			file.mkdirs();
		}

		String originalFileName = multipartFile.getOriginalFilename();
		String originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
		String storedFileName = ImageFileUtils.getRandomString() + originalFileExtension;

		file = new File(filePath + storedFileName);

		try {
			multipartFile.transferTo(file);
		} catch (IOException e) {
			return null;
		}
		logger.debug(storedFileName);
		Image imageFile = new Image(originalFileName, storedFileName);
		logger.debug(imageFile.toString());
		logger.debug(imageFile.getStoredName());
		filedao.addFileInfo(imageFile);
		return imageFile.getStoredName();
	}

	@RequestMapping(value = "/api/post/{postid}/file", method = RequestMethod.GET)
	public Result sendFile(@PathVariable(value = "postid") ObjectId postid, HttpServletResponse response)
			throws UnsupportedEncodingException {
		Image imagefile;

		try {
//			imagefile = filedao.getImagefileByPostId(postid);
			imagefile = imageDao.findOne(postid);
		} catch (EmptyResultDataAccessException e) {
			return Result.failed("No file");
		}

		byte fileByte[];
		String storedFileName = imagefile.getStoredName();
		String originalFileName = imagefile.getOriginalName();
		String originalFileExtension = storedFileName.substring(storedFileName.lastIndexOf("."));
		try {
			fileByte = FileUtils.readFileToByteArray(new File(filePath + storedFileName));
			response.setContentType("image/" + originalFileExtension);
			response.setContentLength(fileByte.length);
			response.setHeader("Content-Disposition",
					"attachment; fileName=" + URLEncoder.encode(originalFileName, "UTF-8") + ";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.getOutputStream().write(fileByte);

			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			return Result.failed("이미지 파일 로딩에 실패했습니다.");
		}
		return Result.success();
	}

}
