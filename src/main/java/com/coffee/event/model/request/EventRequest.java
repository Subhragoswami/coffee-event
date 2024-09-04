package com.coffee.event.model.request;


import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
public class EventRequest {
    private String eventName;
    private String eventLocation;
    @Builder.Default
    private boolean isActive = true;
}
