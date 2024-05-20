package ru.ifmo.main.service;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CamundaDeploymentService {

    private final RepositoryService repositoryService;

    @Autowired
    public CamundaDeploymentService(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }
    public void deleteAllDeployments() {
        List<Deployment> deployments = repositoryService.createDeploymentQuery().list();
        for (Deployment deployment : deployments) {
            repositoryService.deleteDeployment(deployment.getId(), true);
        }
    }


    public void deployProcessDefinition(List<String> bpmnFilesName) {
        DeploymentBuilder deployment = repositoryService.createDeployment();
        bpmnFilesName.forEach(bpmnFileName -> {deployment.addClasspathResource(bpmnFileName).name(bpmnFileName);});
        deployment.deploy();
    }
}