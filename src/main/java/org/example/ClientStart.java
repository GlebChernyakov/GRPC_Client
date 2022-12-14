package org.example;

import com.example.grpc.GreetingServiceGrpc;
import com.example.grpc.GreetingServiceOuterClass;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;

/**
 * Реализация канала для клиента
 *
 * @author Gleb Chernyakov
 */
public class ClientStart {

    public static void main(String[] args) {

        //Задаем канал, по которому клиент будет отправлять или принимать сообщения
        ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8080")
                .usePlaintext().build();


        GreetingServiceGrpc.GreetingServiceBlockingStub stub =
                GreetingServiceGrpc.newBlockingStub(channel);

        GreetingServiceOuterClass.HelloRequest request = GreetingServiceOuterClass.HelloRequest
                .newBuilder().setName("MyName").build();

        // Вызываем метод greeting(), но этот метод описан только на удаленном сервере
        Iterator<GreetingServiceOuterClass.HelloResponse> response =
                stub.greeting(request);

        while (response.hasNext()) {
            System.out.println(response.next());
        }

        channel.shutdownNow();
    }
}
