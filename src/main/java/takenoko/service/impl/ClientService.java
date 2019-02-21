package takenoko.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import takenoko.configuration.RestTemplateConfig;
import takenoko.service.IClientService;

@Service
public class ClientService implements IClientService {

    @Autowired
    private RestTemplateConfig restTemplate;


}
