package com.exihibition.wc_exihibit.kafka;


import com.exihibition.wc_exihibit.dtos.MatchResultMessage;
import com.exihibition.wc_exihibit.services.MatchResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchResultConsumer {

    private final MatchResultService matchResultService;

    @KafkaListener(
            topics =
                    KafkaTopics.MATCH_SIMULATION_RESULT,
            groupId = "simulation-service"
    )
    public void consume(
            MatchResultMessage result
    ) {

        matchResultService
                .processResult(result);
    }
}