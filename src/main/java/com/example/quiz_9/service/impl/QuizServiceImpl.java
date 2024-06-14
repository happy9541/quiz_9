package com.example.quiz_9.service.impl;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.quiz_9.constants.OptionType;
import com.example.quiz_9.constants.ResMessage;
import com.example.quiz_9.entity.Quiz;
import com.example.quiz_9.repository.QuizDao;
import com.example.quiz_9.service.ifs.QuizService;
import com.example.quiz_9.vo.BasicRes;
import com.example.quiz_9.vo.CreateOrUpdateReq;
import com.example.quiz_9.vo.DeleteReq;
import com.example.quiz_9.vo.FillinReq;
import com.example.quiz_9.vo.Question;
import com.example.quiz_9.vo.SearchReq;
import com.example.quiz_9.vo.SearchRes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuizDao quizDao;

	@Override
	public BasicRes createOrUpdate(CreateOrUpdateReq req) {
		// 檢查參數
		BasicRes checkReslt = checkParams(req);
		// checkReslt == null時，表示參數檢查都正確
		if (checkReslt != null) {
			return checkReslt;
		}
		// 因為 Quiz 中 questions 的資料格式是 String，所以要將 req 的 List<Question> 轉成 String
		// 透過 ObjectMapper 可以將物件(類別)轉成 JSON 格式的字串
		ObjectMapper mapper = new ObjectMapper();
		try {
			String questionStr = mapper.writeValueAsString(req.getQuestionList());
//			 若 req 中的 id > 0，表示更新已存在的資料;若 id = 0;則表示要新增
			if(req.getId() > 0) {
				//以下兩種方式擇一
				//使用方法1，透過 findById，若有資料，就會回傳一整筆的資料(可能資料量會較大)
				//使用方法2，因為是透過 existsById 來判斷資料是否存在，所以回傳的資料永遠都只會是一個 bit(0 或 1)
//				//方法1.透過 findById，若有資料，回傳整筆資料
//				Optional<Quiz> op = quizDao.findById(req.getId());
//				//判斷是否有資料
//				if(op.isEmpty()) {//op.isEmpty():表示沒有資料
//					return new BasicRes(ResMessage.UPDATE_ID_NOT_FOUND.getCode(),
//							ResMessage.UPDATE_ID_NOT_FOUND.getMessage());
//				}
//				Quiz quiz = op.get();
//				//設定新值(值從 req 來)
//				//將 req 中的新值設定到舊的 quiz 中，不設定 Id，因為 Id 都一樣
//				quiz.setName(req.getName());
//				quiz.setDescription(req.getDescription());
//				quiz.setStartDate(req.getStartDate());
//				quiz.setEndDate(req.getEndDate());
//				quiz.setQuestions(questionStr);
//				quiz.setPublished(req.isPublished());
				// 方法2.透過 existsById :回傳一個 bit 的值
				// 這邊要判斷從 req 帶進來的 id 是否真的有存在於 DB 中
				// 因為若 id 不存在，後續程式碼再呼叫 JPA 的 save 方法時，會變成新增
				boolean boo = quizDao.existsById(req.getId());
				if(!boo) {//!boo 表示資料不存在
					return new BasicRes(ResMessage.UPDATE_ID_NOT_FOUND.getCode(),
							ResMessage.UPDATE_ID_NOT_FOUND.getMessage());
				}
			}
			//=======================================================================
			
			//上述的 if 可以縮寫成這樣
//			if(req.getId() > 0 && !quizDao.existsById(req.getId())) {
//				return new BasicRes(ResMessage.UPDATE_ID_NOT_FOUND.getCode(),
//						ResMessage.UPDATE_ID_NOT_FOUND.getMessage());
//			}
			
			//=======================================================================
			
//			Quiz quiz = new Quiz(req.getName(),req.getDescription(),req.getStartDate(),
//					req.getEndDate(),questionStr,req.isPublished());
//			quizDao.save(quiz);
			// 因為變數 quiz 只使用一次，因此可以使用匿名類別方式去撰寫(不需要變數接)
			// new Quiz() 中帶入 req.getId() 是PK，在呼叫 save 時，會先去檢查 PK 是否有存在於 DB 中，
			// 若存在-->更新;不存在-->新增
			// req 中沒有該欄位時，預設是 0 ，因為 id 的資料型態是 int
			quizDao.save(new Quiz(req.getId(),req.getName(), req.getDescription(), req.getStartDate(), req.getEndDate(),
					questionStr, req.isPublished()));
		} catch (JsonProcessingException e) {
			return new BasicRes(ResMessage.JSON_PROCESSING_EXCEPTION.getCode(),
					ResMessage.JSON_PROCESSING_EXCEPTION.getMessage());
		}

		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}

	private BasicRes checkParams(CreateOrUpdateReq req) {
		// 檢查問卷參數
		// StringUtils.hasText(字串):會檢查字串是否為 null 或空字串或全空白字串，若市府和三種其中一種會回傳false
		// 前面加驚嘆號表示反向的意思，若字串的檢查結果是 false 的就會進入 if 的實作區塊
		// !StringUtils.hasText(account) 等同於StringUtils.hasText(req.getName()) == FLASE
		// 有驚嘆號 沒驚嘆號
		if (!StringUtils.hasText(req.getName())) {
			return new BasicRes(ResMessage.PARAM_QUIZ_NAME_ERROR.getCode(), 
					ResMessage.PARAM_QUIZ_NAME_ERROR.getMessage());
		}
		if (!StringUtils.hasText(req.getDescription())) {
			return new BasicRes(ResMessage.PARAM_DESCRIPTION_ERROR.getCode(),
					ResMessage.PARAM_DESCRIPTION_ERROR.getMessage());
		}
		//1.開始時間不能小於等於當前時間
		// LocalDate.now(): 取得系統當前時間
		// req.getStartDate().isAfter(LocalDate.now()): 若 req 中的開始時間比當前時間晚，會得到 true
		// !req.getStartDate().isAfter(LocalDate.now()): 前面有加驚嘆號，表示會得到相反的結果，就是開始時間
		//                                               會等於小於當前時間
		if (req.getStartDate() == null || !req.getStartDate().isAfter(LocalDate.now())) {
			return new BasicRes(ResMessage.PARAM_START_DATE_ERROR.getCode(),
					ResMessage.PARAM_START_DATE_ERROR.getMessage());
		}
		//程式碼有執行到這行時，表示開始時間一定大於等於當前時間
		//所以後續檢查結束時間時，只要確認結束時間是大於等於開始時間即可，因為只要結束時間是大於等於開始時間，
		//就一定會是大於等於當前時間，所以不需要判斷!req.getEndDate().isAfter(LocalDate.now())
		if (req.getEndDate() == null || req.getEndDate().isBefore(req.getStartDate())) {
			return new BasicRes(ResMessage.PARAM_END_DATE_ERROR.getCode(),
					ResMessage.PARAM_END_DATE_ERROR.getMessage());
		}
		// 檢查問題參數
		if (CollectionUtils.isEmpty(req.getQuestionList())) {
			return new BasicRes(ResMessage.PARAM_QUESTION_NOT_FOUND.getCode(),
					ResMessage.PARAM_QUESTION_NOT_FOUND.getMessage());
		}
		// 一張問卷可能會有多個問題，所以要逐筆檢查每一題的參數
		for (Question item : req.getQuestionList()) {
			if (item.getId() <= 0) {
				return new BasicRes(ResMessage.PARAM_QUESTION_ID_ERROR.getCode(),
						ResMessage.PARAM_QUESTION_ID_ERROR.getMessage());
			}
			if (!StringUtils.hasText(item.getTitle())) {
				return new BasicRes(ResMessage.PARAM_TITLE_ERROR.getCode(),
						ResMessage.PARAM_TITLE_ERROR.getMessage());
			}
			
			if (!StringUtils.hasText(item.getType())) {
				return new BasicRes(ResMessage.PARAM_TYPE_ERROR.getCode(), ResMessage.
						PARAM_TYPE_ERROR.getMessage());
			}
			// 當 option_type 是單選或多選時，options 就不能是空字串
			// 但 option_type 是文字時，options 就允許是空字串
			// 以下條件檢查: 當 option_type 是單選或多選時，且options 是空字串，就返回錯誤
			if(item.getType().equals(OptionType.SINGLE_CHOICE.getType())
					|| item.getType().equals(OptionType.MULTI_CHOICE.getType())) {
				if (!StringUtils.hasText(item.getOptions())) {
					return new BasicRes(ResMessage.PARAM_OPTIONS_ERROR.getCode(), 
							ResMessage.PARAM_OPTIONS_ERROR.getMessage());
				}
			}

			//以下是上述2個 if 合併寫法: (條件1 || 條件2) && 條件3
			//                     第一個 if 條件式  && 第二個 if 條件式 
//          if((item.getType().equals(OptionType.SINGLE_CHOICE.getType())
//			     || item.getType().equals(OptionType.MULTI_CHOICE.getType())) 
//			     && !StringUtils.hasText(item.getOptions())) {
//			    return new BasicRes(ResMessage.PARAM_OPTIONS_ERROR.getCode(), 
//			      ResMessage.PARAM_OPTIONS_ERROR.getMessage());
//   }

	}
		return null;

	}

	@Override
	public SearchRes search(SearchReq req) {
		String name = req.getName();
		
		LocalDate start = req.getStartDate();
		
		LocalDate end = req.getEndDate();
		
		// 假設 name 是 null 或是全空白字串，可以視為沒有輸入條件值，就表示要取得全部
		// JPA 的 containing 方法，條件值是空字串時，會搜尋全部
		// 所以要把 name 的值是 null 或是全空白字串時，轉換成空字串
		if( !StringUtils.hasText(name)) {
			name ="";
		}
		if(start == null) {
			start = LocalDate.of(1970, 1, 1);
		}
		if(end == null) {
			end = LocalDate.of(2999, 12, 31);
		}
		return new SearchRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(),
				quizDao.findByNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(name,
						start, end));
	}

	@Override
	public BasicRes delete(DeleteReq req) {
		// 參數檢查
		if(!CollectionUtils.isEmpty(req.getIdList())) {
			//刪除問卷
			try {
			quizDao.deleteAllById(req.getIdList());
		} catch (Exception e) {
			// 當 deleteAllById 方法中，id 的值不存在，JPA 會報錯
			// 因為在刪除之前， JPA會先搜帶入的 id 值，若沒結果就會報錯
			// 由於實際上也沒刪除任資料，因此就不需要對這個Exception 做處理
		}
		}
		
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}

	@Override
	public BasicRes fillIn(FillinReq req) {
		return null;
	}


	
}
