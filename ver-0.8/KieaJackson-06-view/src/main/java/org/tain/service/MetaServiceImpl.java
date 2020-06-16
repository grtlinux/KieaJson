package org.tain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tain.domain.Meta;
import org.tain.repository.MetaRepository;

@Service
public class MetaServiceImpl implements MetaService {

	@Autowired
	private MetaRepository metaRepository;
	
	@Override
	public Meta findByName(String name) {
		return this.metaRepository.findByName(name);
	}
}
