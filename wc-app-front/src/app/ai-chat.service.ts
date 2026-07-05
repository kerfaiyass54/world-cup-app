import { Injectable, inject } from '@angular/core';

import {
  HttpClient
} from '@angular/common/http';

import {
  Observable
} from 'rxjs';

export interface Conversation {

  id: string;

  title: string;

  user_email: string;

  created_at: string;

}

export interface Message {

  id?: string;

  conversation_id: string;

  role: 'user' | 'assistant';

  content: string;

  created_at?: string;

}

export interface ChatRequest {

  conversation_id: string;

  message: string;

}

export interface ChatResponse {

  response: string;

}

@Injectable({
  providedIn: 'root'
})
export class AiChatService {

  private http = inject(HttpClient);

  private api =
    'http://localhost:8000/api';

  createConversation(
    email: string,
    title: string
  ): Observable<Conversation> {

    return this.http.post<Conversation>(
      `${this.api}/conversations`,
      {
        user_email: email,
        title
      }
    );

  }

  getConversations(
    email: string
  ): Observable<Conversation[]> {

    return this.http.get<Conversation[]>(
      `${this.api}/conversations/${email}`
    );

  }

  deleteConversation(
    conversationId: string
  ): Observable<any> {

    return this.http.delete(
      `${this.api}/conversations/${conversationId}`
    );

  }

  getMessages(
    conversationId: string
  ): Observable<Message[]> {

    return this.http.get<Message[]>(
      `${this.api}/messages/${conversationId}`
    );

  }

  sendMessage(
    request: ChatRequest
  ): Observable<ChatResponse> {

    return this.http.post<ChatResponse>(
      `${this.api}/chat`,
      request
    );

  }

}
