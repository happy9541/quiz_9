package com.example.quiz_9;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.example.quiz_9.constants.OptionType;
import com.example.quiz_9.repository.QuizDao;
import com.example.quiz_9.service.ifs.QuizService;
import com.example.quiz_9.vo.BasicRes;
import com.example.quiz_9.vo.CreateOrUpdateReq;
import com.example.quiz_9.vo.Question;

@SpringBootTest
public class QuizServiceTests {

	@Autowired
	private QuizService quizService;
	
	@Autowired
	private QuizDao quizDao;

	@Test
	public void createTest() {
		List<Question>questionList =new ArrayList<>();
		questionList.add(new Question(1,"���d�\","�Q����,���ޱ�,�γ�,�N���L",//
		OptionType.SINGLE_CHOICE.getType(),true));
		questionList.add(new Question(2,"����","1���\,2���\,3���\,4���\",//
				OptionType.SINGLE_CHOICE.getType(),true));
		questionList.add(new Question(3,"����","�ަת���,���A����,�z�����a��(��),��X����",//
				OptionType.SINGLE_CHOICE.getType(),true));
		CreateOrUpdateReq req = new CreateOrUpdateReq("���\�Yԣ?","���\�Yԣ?",LocalDate.of(2024, 06, 01 ),//
				LocalDate.of(2024, 06, 01 ),questionList,true);
		BasicRes res = quizService.createOrUpdate(req);
		Assert.isTrue(res.getStatusCode() == 200,"create test false!!!");
		//�R�����ո�� TODO
	}
	
	@Test
	public void createNameErrorTest() {
		List<Question>questionList =new ArrayList<>();
		questionList.add(new Question(1,"���d�\","�Q����,���ޱ�,�γ�,�N���L",//
		OptionType.SINGLE_CHOICE.getType(),true));
		questionList.add(new Question(2,"����","1���\,2���\,3���\,4���\",//
				OptionType.SINGLE_CHOICE.getType(),true));
		questionList.add(new Question(3,"����","�ަת���,���A����,�z�����a��(��),��X����",//
				OptionType.SINGLE_CHOICE.getType(),true));
		CreateOrUpdateReq req = new CreateOrUpdateReq("","���\�Yԣ?",LocalDate.of(2024, 06, 01 ),//
				LocalDate.of(2024, 06, 01 ),questionList,true);
		BasicRes res = quizService.createOrUpdate(req);
		Assert.isTrue(res.getMassage().equalsIgnoreCase("Param name error"),"create test false!!!");
	}
	
	@Test
	public void createStatDateErrorTest() {
		List<Question>questionList =new ArrayList<>();
		questionList.add(new Question(1,"���d�\","�Q����,���ޱ�,�γ�,�N���L",//
		OptionType.SINGLE_CHOICE.getType(),true));
		questionList.add(new Question(2,"����","1���\,2���\,3���\,4���\",//
				OptionType.SINGLE_CHOICE.getType(),true));
		questionList.add(new Question(3,"����","�ަת���,���A����,�z�����a��(��),��X����",//
				OptionType.SINGLE_CHOICE.getType(),true));
//		���ѬO2024/05/30�A�ҥH�}�l�ɶ�����O��ѩάO���e
		CreateOrUpdateReq req = new CreateOrUpdateReq("���\�Yԣ?","���\�Yԣ?",LocalDate.of(2024, 5, 30 ),//
				LocalDate.of(2024, 06, 01 ),questionList,true);
		BasicRes res = quizService.createOrUpdate(req);
		Assert.isTrue(res.getMassage().equalsIgnoreCase("Param start date error"),"create test false!!!");
	}
	
	@Test
	public void createTest1() {
		List<Question>questionList =new ArrayList<>();
		questionList.add(new Question(1,"���d�\","�Q����,���ޱ�,�γ�,�N���L",//
		OptionType.SINGLE_CHOICE.getType(),true));
		//���� name error
		CreateOrUpdateReq req = new CreateOrUpdateReq("","���\�Yԣ?",LocalDate.of(2024, 06, 01 ),//
				LocalDate.of(2024, 06, 01 ),questionList,true);
		BasicRes res = quizService.createOrUpdate(req);
		Assert.isTrue(res.getMassage().equalsIgnoreCase("Param name error"),"create test false!!!");
		//���� start date error�A���ѬO2024/05/30�A�ҥH�}�l�ɶ�����O��ѩάO���e
		req = new CreateOrUpdateReq("���\�Yԣ?","���\�Yԣ?",LocalDate.of(2024, 05, 30 ),//
				LocalDate.of(2024, 06, 01 ),questionList,true);
		res = quizService.createOrUpdate(req);
		Assert.isTrue(res.getMassage().equalsIgnoreCase("Param start date error"),"create test false!!!");
		//���� end date error:������������}�l�����
		req = new CreateOrUpdateReq("���\�Yԣ?","���\�Yԣ?",LocalDate.of(2024, 06, 30 ),//
				LocalDate.of(2024, 06, 01 ),questionList,true);
		res = quizService.createOrUpdate(req);
		Assert.isTrue(res.getMassage().equalsIgnoreCase("Param end date error"),"create test false!!!");
		//�Ѿl���޿�����P�_������A�̫�~�|�O���զ��\������
		req = new CreateOrUpdateReq("���\�Yԣ?","���\�Yԣ?",LocalDate.of(2024, 06, 01 ),//
				LocalDate.of(2024, 06, 01 ),questionList,true);
		res = quizService.createOrUpdate(req);
		Assert.isTrue(res.getStatusCode() == 200,"create test false!!!");
	}
	
	
	@Test
	public void createNameErrorTest() {
		List<Question>questionList =new ArrayList<>();
		questionList.add(new Question(1,"���d�\","�Q����,���ޱ�,�γ�,�N���L",//
		OptionType.SINGLE_CHOICE.getType(),true));
		questionList.add(new Question(2,"����","1���\,2���\,3���\,4���\",//
				OptionType.SINGLE_CHOICE.getType(),true));
		questionList.add(new Question(3,"����","�ަת���,���A����,�z�����a��(��),��X����",//
				OptionType.SINGLE_CHOICE.getType(),true));
		CreateOrUpdateReq req = new CreateOrUpdateReq("","���\�Yԣ?",LocalDate.of(2024, 06, 01 ),//
				LocalDate.of(2024, 06, 01 ),questionList,true);
		BasicRes res = quizService.createOrUpdate(req);
		Assert.isTrue(res.getMassage().equalsIgnoreCase("Param name error"),"create test false!!!");
		System.out.println("==================================================================");
	}
	

}
