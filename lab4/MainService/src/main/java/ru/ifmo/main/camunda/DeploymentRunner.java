package ru.ifmo.main.camunda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.ifmo.main.service.CamundaDeploymentService;

import java.util.List;

@Component
public class DeploymentRunner implements CommandLineRunner {

    private final CamundaDeploymentService camundaDeploymentService;

    @Autowired
    public DeploymentRunner(CamundaDeploymentService camundaDeploymentService) {
        this.camundaDeploymentService = camundaDeploymentService;
    }


    @Override
    public void run(String... args) throws Exception {
        camundaDeploymentService.deleteAllDeployments();
        camundaDeploymentService.deployProcessDefinition(List.of("auth-process.bpmn", "register-process.bpmn"));

    }
}