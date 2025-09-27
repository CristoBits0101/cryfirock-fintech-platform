package com.cryfirock.auth.service.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Aspecto para registrar las operaciones del servicio de usuarios
 * Ejecuta funciones antes, después y alrededor de los métodos del servicio
 */
@Aspect
public class UserAspect {

    /**
     * Registro para rastrear la ejecución de métodos
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Registra antes de la ejecución de los métodos de UserService
     *
     * @param joinPoint proporciona acceso a la información del método
     */
    @Before("execution(* com.cryfirock.auth.service.services.UserServiceImpl.*(..))")
    public void loggerBefore(JoinPoint joinPoint) {
        // Obtiene el nombre del método y los argumentos
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());

        // Registra antes de ejecutar el método con sus parámetros
        logger.info("Antes de ejecutar: " + method + " con argumentos " + args);
    }

    /**
     * Registra después de la ejecución de los métodos de UserService (sin importar el resultado)
     *
     * @param joinPoint proporciona acceso a la información del método
     */
    @After("execution(* com.cryfirock.auth.service.services.UserServiceImpl.*(..))")
    public void loggerAfter(JoinPoint joinPoint) {
        // Obtiene el nombre del método y los argumentos
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());

        // Registra después de ejecutar el método con sus parámetros
        logger.info("Después de ejecutar: " + method + " con argumentos " + args);
    }

    /**
     * Registra tras el retorno exitoso de los métodos de UserService
     *
     * @param joinPoint proporciona acceso a la información del método
     */
    @AfterReturning("execution(* com.cryfirock.auth.service.services.UserServiceImpl.*(..))")
    public void loggerAfterReturning(JoinPoint joinPoint) {
        // Obtiene el nombre del método y los argumentos
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());

        // Registra tras la ejecución exitosa del método con sus parámetros
        logger.info("Después de una ejecución exitosa: " + method + " con argumentos " + args);
    }

    /**
     * Registra cuando ocurre una excepción en los métodos de UserService
     *
     * @param joinPoint proporciona acceso a la información del método
     */
    @AfterThrowing("execution(* com.cryfirock.auth.service.services.UserServiceImpl.*(..))")
    public void loggerAfterThrowing(JoinPoint joinPoint) {
        // Obtiene el nombre del método y los argumentos
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());

        // Registra después de la excepción en el método con sus parámetros
        logger.error("Después de una excepción en: " + method + " con argumentos " + args);
    }

    /**
     * Envuelve los métodos de UserService para un registro completo
     *
     * @param joinPoint proporciona acceso para continuar con la ejecución del método
     * @return el resultado del método
     * @throws Throwable si el método lanza una excepción
     */
    @Around("execution(* com.cryfirock.auth.service.services.UserServiceImpl.*(..))")
    public Object loggerAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // Obtiene el nombre del método y los argumentos
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());

        // Registra la entrada al método con sus parámetros
        Object result = null;

        // Ejecuta el método y registra los resultados y posibles excepciones
        try {
            logger.info("Entrando al método " + method + "() con parámetros " + args);
            result = joinPoint.proceed();
            logger.info("El método " + method + "() devolvió: " + result);
            return result;
        } catch (Throwable e) {
            logger.error("Error en el método " + method + "(): " + e.getMessage());
            throw e;
        }
    }
}