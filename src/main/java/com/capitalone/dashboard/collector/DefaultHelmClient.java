package com.capitalone.dashboard.collector;

import com.capitalone.dashboard.builder.ModelBuilder;
import com.capitalone.dashboard.command.util.CommandLineUtil;
import com.capitalone.dashboard.model.BaseModel;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * HelmClient implementation that uses helm command line to fetch information about Kube Deployments
 */

@Component
public class DefaultHelmClient implements HelmClient {
    private static final Log LOG = LogFactory.getLog(DefaultHelmClient.class);

    @Override
    public String getCommandResult(String command, Long timeout) throws RuntimeException, IOException, InterruptedException {
        return CommandLineUtil.execCommand(command, timeout);
    }

    @Override
    public List<? extends BaseModel> getCommandResultComposed(String command, Long timeout,
                                                              Class<?> clazz) throws RuntimeException, IOException, InterruptedException {
        String result = getCommandResult(command, timeout);
        List<BaseModel> objects = new ArrayList<>();

        if (result == null) {
            LOG.error("Empty response for command: " + command);
        } else {
            final String[] resultLines = result.split("\\r?\\n");
            final List<String> headers = Arrays.stream(resultLines[0].trim().split("\\t")).collect(Collectors.toList());

            Arrays.stream(resultLines).skip(1).forEach(line -> {
                final List<String> values = Arrays.stream(line.trim().split("\\t")).collect(Collectors.toList());
                Map<String, String> args = new HashMap<>();
                IntStream.range(0, values.size()).forEach(i -> args.put(headers.get(i).trim(), values.get(i).trim()));
                objects.add(ModelBuilder.createModelObject(clazz, args));
            });
        }
        return objects;
    }

    public JsonNode parseAsObject(String response) {
        try {
            JsonParser parser = new JsonFactory()
                    .createParser(response)
                    .enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)
                    .enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
            return new ObjectMapper().readTree(parser);
        } catch (IOException e) {
            LOG.error(e.getMessage());
            return null;
        }
    }
}

