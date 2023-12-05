package com.ssafy.qna.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.qna.model.QnaDto;
import com.ssafy.qna.model.QnaListDto;
import com.ssafy.qna.model.mapper.QnaMapper;

@Service
public class QnaServiceImpl implements QnaService {
	
	private QnaMapper qnaMapper;
	
	@Autowired
	public QnaServiceImpl(QnaMapper qnaMapper) {
		super();
		this.qnaMapper = qnaMapper;
	}

	@Override
	@Transactional
	public void writeArticle(QnaDto qnaDto) throws Exception {
		qnaMapper.writeArticle(qnaDto);
		
	}

	@Override
	public QnaListDto listArticle(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int sizePerPage = Integer.parseInt(map.get("spp") == null ? "20" : map.get("spp"));
		int start = currentPage * sizePerPage - sizePerPage;
		param.put("start", start);
		param.put("listsize", sizePerPage);

		String key = map.get("key");
		param.put("key", key == null ? "" : key);
		if ("user_id".equals(key))
			param.put("key", key == null ? "" : "b.user_id");
		List<QnaDto> list = qnaMapper.listArticle(param);

		if ("user_id".equals(key))
			param.put("key", key == null ? "" : "user_id");
		int totalArticleCount = qnaMapper.getTotalArticleCount(param);
		int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;

		QnaListDto qnaListDto = new QnaListDto();
		qnaListDto.setArticles(list);
		qnaListDto.setCurrentPage(currentPage);
		qnaListDto.setTotalPageCount(totalPageCount);

		return qnaListDto;
	}

	@Override
	public QnaDto getArticle(int articleNo) throws Exception {
		return qnaMapper.getArticle(articleNo);
	}

	@Override
	@Transactional
	public void updateHit(int articleNo) throws Exception {
		qnaMapper.updateHit(articleNo);
		
	}

	@Override
	@Transactional
	public void modifyArticle(QnaDto qnaDto) throws Exception {
		qnaMapper.modifyArticle(qnaDto);
		
	}

	@Override
	@Transactional
	public void deleteArticle(int articleNo) throws Exception {
		qnaMapper.deleteArticle(articleNo);
		
	}
	


}
