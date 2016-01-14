package cafein.dear;

import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cafein.cafe.Dear;
import cafein.repository.DearRepository;
import cafein.util.Result;

@RestController
@RequestMapping("/api/place/{placeId}/dear")
public class APIDearController {
	private static final Logger logger = LoggerFactory.getLogger(APIDearController.class);
	int defaultPageSize = 10;

	@Autowired
	private DearRepository dearDao;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public Result getDearList(@PathVariable("placeId") ObjectId placeId, @RequestParam("page") Integer nPage) {
		logger.debug(placeId.toString());
		List<Dear> result = dearDao.findByPlaceId(placeId, new PageRequest(nPage, defaultPageSize, Sort.Direction.DESC, "totalPostNum")).getContent();
		logger.debug(result.toString());
		if (result.isEmpty()) {
			return Result.failed("No more data.");
		}
		return Result.success(result);
	}

	// 일단 장소만 이동.
	// @RequestMapping(value = "recommend", method = RequestMethod.GET)
	// public Result getRecommendedDear(@PathVariable String placeId) {
	//
	// if (!Validation.isValidParameter(placeId) ||
	// !Validation.isValidParameterType(placeId)) {
	// throw new IllegalAPIPathException();
	// }
	// return Result.success(candidatedao.getRecommendedDears(placeId));
	// }
}