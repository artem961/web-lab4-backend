package lab4.backend.services.utils;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import lab4.backend.services.exceptions.ServiceException;

@Interceptor
@CatchAllExceptions
public class ServiceExceptionInterceptor {

    @AroundInvoke
    public Object handleException(InvocationContext ctx){
        try{
            return ctx.proceed();
        } catch (Exception e) {
            CatchAllExceptions annotation = getAnnotation(ctx);
            String message = annotation != null ? annotation.message() : "Service operation failed";
            throw new ServiceException(message);
        }
    }

    private CatchAllExceptions getAnnotation(InvocationContext context) {
        CatchAllExceptions methodAnnotation = context.getMethod()
                .getAnnotation(CatchAllExceptions.class);

        if (methodAnnotation != null) {
            return methodAnnotation;
        }

        return context.getTarget().getClass()
                .getAnnotation(CatchAllExceptions.class);
    }
}
