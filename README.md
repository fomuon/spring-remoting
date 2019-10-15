# spring remoting
> spring remoting 을 사용한 원격 서비스 생성 및 호출을 위한 설정을 간편하게 할 수 있습니다.


## 원격 서비스 제공 (provider)

**설정**

```java
@Configuration
@Import(HttpInvokerRemotingServiceConfigurer.class)
public class ProviderConfig {
...
```

**Service 등록**

```java
@RemotingService(value = "cabBookingService")
public class CabBookingServiceImpl implements CabBookingService {

	@Override
	public Booking bookRide(String pickUpLocation) throws BookingException {
		if (random() < 0.3) throw new BookingException("Cab unavailable");
		return new Booking(randomUUID().toString());
	}
}
```

## 원격 서비스 사용 (consumer)

**설정**

```java
@Configuration
@HttpInvokerScan(baseUrl = "http://localhost:8081/",
		basePackages = "com.naver.spring.remoting.example.core")
public class ConsumerConfig {
...
```

**호출**

```java
@Autowired
private CabBookingService cabBookingService;
```
