package kr.lsj.common.lib.net.web.client;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;


/**
 * <pre>
 * 서비스 명 : VAS_v2
 * 패키지 명 : kr.co.wkit.hdsvas.lib.net.web.client
 * 클래스 명 : WebClientAgent
 * 설     명 :
 *
 * ==============================================================================
 *
 * </pre>
 * @date 2023-11-06
 * @author jiny
 * @version 1.0.0
 */
@Slf4j
public class WebClientAgent<T, P> {

	private final WebClient webClient;

	public WebClientAgent() {
		this("http://localhost:8080", 10);
	}

	public WebClientAgent(String _baseurl, int _timeoutSeconds) {
		this.webClient = getWebClient(_baseurl, _timeoutSeconds);
	}


	public Flux<?> getRequestFlux(String _uri, T resBodyType) {
		return webClient.get()
				.uri(_uri)
				.retrieve()
				.bodyToFlux(resBodyType.getClass());
	}

	public Disposable getRequestMonoVoid(String _uri){
		return  webClient.get()
				.uri(_uri)
				.retrieve()
				.toBodilessEntity()
				.subscribe();
	}


	public Mono<?> getRequestMono(String _uri, T _resBodyType) {
		return webClient.get()
				.uri(_uri)
				.retrieve()
				.bodyToMono(_resBodyType.getClass());
	}


	public Flux<?> postRequest(String _uri, T _resBodyType, P _param) {
		return webClient.post()
				.uri(_uri)
				.body(Mono.just(_param), _param.getClass())
				.retrieve()
				.bodyToFlux(_resBodyType.getClass());
	}


	public Flux<?> patchRequest(String _uri, T _resBodyType, P _param) {
		return webClient.patch()
				.uri(_uri)
				.body(Mono.just(_param), _param.getClass())
				.retrieve()
				.bodyToFlux(_resBodyType.getClass());
	}


	public Flux<Void> deleteRequest(String _uri) {
		return webClient.delete()
				.uri(_uri)
				.retrieve()
				.bodyToFlux(Void.class);
	}




	private WebClient getWebClient(String _baseUrl, int _timeoutSeconds) {
		HttpClient httpClient = HttpClient.create()
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, _timeoutSeconds*1000)
				.doOnConnected(conn ->
						conn.addHandlerLast(new ReadTimeoutHandler(_timeoutSeconds))
							.addHandlerLast(new WriteTimeoutHandler(_timeoutSeconds))
				);

		ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

		return WebClient.builder()
				.baseUrl(_baseUrl)
				.clientConnector(connector)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}

}
