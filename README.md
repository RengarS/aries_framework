# aries_framework

仿照Spring和SpringMVC实现的一个Java web框架。

主要功能有：IoC、AOP、请求转发等，集成了mybatis和thymeleaf。

这个框架作为我的毕业设计，很多地方不是很完善，实现上也有非常大的优化、拓展空间。

框架实现原理很简单，主要流程是： 1.包扫描 -- 扫描指定包下的所有java类，并将其加载，把加载后的class放进去一个Class Set里。 2.Bean容器：扫描Class Set中的所有类，如果被@Service、@Controller、@Component注解的类，加载进Bean容器 3.Field赋值：扫描Bean容器中的所有类，扫描类中的Field，如果Field被@Autowired注解，Field field = BeamMap.get（filed.getType（））。 4.HandlerMapping：扫描所有的Controller，将@RequestMapping中的mapping和method封装成一个Request对象，将方法和类封装成一个Handler对象，放进去Map中。

当外部请求到达的时候，获取请求的URI和requestMethod封装成一个Request对象，从Map中获取到Handler，随后进行参数解析-->方法调用-->返回值处理。


模块说明：
    aries_boot 是项目的核心，主要代码实现都在其中
    context 是框架的IoC容器
    mybatis_aries 是框架整合Mybatis的工具
    
阅读建议：
    最好从aries_boot 中的com.aries.aries_boot.DispatcherServlet.java开始阅读
    
    
#### 测试项目
https://github.com/RengarS/final-test
