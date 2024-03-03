package io.gaitian.reporter.base;

import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;

public class ReporterCompose extends ComposeContainer {

    public static String COLLABORA_NAME = "collabora";
    public static int COLLABORA_PORT = 9980;

    public ReporterCompose() {
        super(new File("compose.yml"));
    }

    public ReporterCompose withCollabora() {
        return (ReporterCompose)withExposedService(COLLABORA_NAME, COLLABORA_PORT, Wait.forHealthcheck());
    }

    public String getCollaboraUrl() {
        return "http://" +
                getServiceHost(COLLABORA_NAME, COLLABORA_PORT) + ":" +
                getServicePort(COLLABORA_NAME, COLLABORA_PORT);
    }
}
