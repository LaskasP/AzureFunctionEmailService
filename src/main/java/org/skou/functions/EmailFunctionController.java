package org.skou.functions;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import org.skou.functions.model.EmailDTO;
import org.springframework.cloud.function.adapter.azure.FunctionInvoker;


public class EmailFunctionController extends FunctionInvoker<EmailDTO, String> {
    private static final String PASS_VALID_BODY = "Please pass a valid body";

    @FunctionName("mailService")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<EmailDTO> request,
            final ExecutionContext context) {
        context.getLogger().info("Begin: AzureFunctionHandler");
        EmailDTO mail = request.getBody();
        boolean isValidMail = mail != null;
        HttpResponseMessage response;
        if (isValidMail) {
            response = request.createResponseBuilder(HttpStatus.OK).body(handleRequest(mail, context)).build();
        } else {
            response = request.createResponseBuilder(HttpStatus.BAD_REQUEST).body(PASS_VALID_BODY).build();
        }
        context.getLogger().info("Begin: AzureFunctionHandler");
        return response;
    }
}