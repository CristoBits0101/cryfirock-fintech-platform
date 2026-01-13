package com.cryfirock.auth.aspect;

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
import org.springframework.stereotype.Component;

/**
 * 1. Aspecto para la obtención de información de métodos en UserServiceImpl.
 * 2. Proporciona logging antes y después de la ejecución de los métodos.
 * 3. Maneja logging para ejecuciones exitosas y excepciones.
 * 4. Utiliza AOP de Spring para interceptar llamadas a métodos.
 */
@Aspect
@Component
public class UserAspect {
  // 1. Logger para registrar mensajes de log.
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  /**
   * 1. Log antes de ejecutar métodos de la clase UserServiceImpl.
   * 2. Registra el nombre del método y sus argumentos.
   * 
   * @param joinPoint Información del punto de unión.
   */
  @Before("execution(* com.cryfirock.auth.service.UserServiceImpl.*(..))")
  public void loggerBefore(JoinPoint joinPoint) {
    // Obtener el nombre del método y sus argumentos.
    String methodName = joinPoint.getSignature().getName();
    String methodArgs = Arrays.toString(joinPoint.getArgs());
    // Mostrar el log antes de la ejecución del método.
    logger.info("Antes de ejecutar: " + methodName + " con argumentos " + methodArgs);
  }

  /**
   * 1. Log después de ejecutar métodos de la clase UserServiceImpl.
   * 2. Registra el nombre del método y sus argumentos.
   * 
   * @param joinPoint Información del punto de unión.
   */
  @After("execution(* com.cryfirock.auth.service.UserServiceImpl.*(..))")
  public void loggerAfter(JoinPoint joinPoint) {
    // Obtener el nombre del método y sus argumentos.
    String methodName = joinPoint.getSignature().getName();
    String methodArgs = Arrays.toString(joinPoint.getArgs());
    // Mostrar el log después de la ejecución del método.
    logger.info("Después de ejecutar: " + methodName + " con argumentos " + methodArgs);
  }

  /**
   * 1. Log después de una ejecución exitosa de métodos en UserServiceImpl.
   * 2. Registra el nombre del método y sus argumentos.
   * 
   * @param joinPoint Información del punto de unión.
   */
  @AfterReturning("execution(* com.cryfirock.auth.service.UserServiceImpl.*(..))")
  public void loggerAfterReturning(JoinPoint joinPoint) {
    // Obtener el nombre del método y sus argumentos.
    String method = joinPoint.getSignature().getName();
    String args = Arrays.toString(joinPoint.getArgs());
    // Mostrar el log después de una ejecución exitosa.
    logger.info("Después de una ejecución exitosa: " + method + " con argumentos " + args);
  }

  /**
   * 1. Log después de que un método en UserServiceImpl lanza una excepción.
   * 2. Registra el nombre del método y sus argumentos.
   * 
   * @param joinPoint Información del punto de unión.
   */
  @AfterThrowing("execution(* com.cryfirock.auth.service.UserServiceImpl.*(..))")
  public void loggerAfterThrowing(JoinPoint joinPoint) {
    // Obtener el nombre del método y sus argumentos.
    String method = joinPoint.getSignature().getName();
    String args = Arrays.toString(joinPoint.getArgs());
    // Mostrar el log después de una excepción.
    logger.error("Después de una excepción en: " + method + " con argumentos " + args);
  }

  /**
   * 1. Log alrededor de la ejecución de métodos en UserServiceImpl.
   * 2. Registra el nombre del método y sus argumentos.
   * 3. Registra el valor retornado y cualquier excepción lanzada.
   * 
   * @param joinPoint Información del punto de unión.
   * @return El valor retornado por el método interceptado.
   * @throws Throwable Excepciones lanzadas por el método interceptado.
   */
  @Around("execution(* com.cryfirock.auth.service.UserServiceImpl.*(..))")
  public Object loggerAround(ProceedingJoinPoint joinPoint) throws Throwable {
    // Obtener el nombre del método y sus argumentos.
    String method = joinPoint.getSignature().getName();
    String args = Arrays.toString(joinPoint.getArgs());
    // Mostrar el log alrededor de la ejecución del método.
    try {
      logger.info("Entrando al método " + method + "() con parámetros " + args);
      Object result = joinPoint.proceed();
      logger.info("El método " + method + "() devolvió: " + result);
      return result;
    } catch (Throwable e) {
      logger.error("Error en el método " + method + "(): " + e.getMessage());
      throw e;
    }
  }
}
