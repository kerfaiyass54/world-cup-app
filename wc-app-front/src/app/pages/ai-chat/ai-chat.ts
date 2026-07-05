import {
  Component,
  OnInit,
  inject,
  signal,
  ChangeDetectorRef,
  ElementRef,
  ViewChild
} from '@angular/core';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import {
  AiChatService,
  Conversation,
  Message
} from '../../ai-chat.service';

@Component({
  selector: 'app-ai-chat',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './ai-chat.html',
  styleUrl: './ai-chat.css'
})
export class AiChat implements OnInit {

  private chatService =
    inject(AiChatService);

  private cdr =
    inject(ChangeDetectorRef);

  @ViewChild('messagesContainer')
  messagesContainer?: ElementRef;

  conversations =
    signal<Conversation[]>([]);

  selectedConversation =
    signal<Conversation | null>(null);

  messages =
    signal<Message[]>([]);

  prompt =
    signal('');

  loading =
    signal(false);

  sending =
    signal(false);

  error =
    signal('');

  userEmail =
    signal(
      sessionStorage.getItem('email') ?? ''
    );

  ngOnInit(): void {

    this.loadConversations();

  }

  loadConversations(): void {

    const email =
      this.userEmail();

    if (!email) {

      this.error.set(
        'User email not found'
      );

      return;

    }

    this.loading.set(true);

    this.chatService
      .getConversations(email)
      .subscribe({

        next: (data) => {

          this.conversations.set(data);

          this.loading.set(false);

          if (
            data.length > 0
          ) {

            this.selectConversation(
              data[0]
            );

          }

          this.cdr.detectChanges();

        },

        error: (err) => {

          console.error(err);

          this.error.set(
            'Failed to load conversations'
          );

          this.loading.set(false);

          this.cdr.detectChanges();

        }

      });

  }

  createConversation(): void {

    const email =
      this.userEmail();

    if (!email) {
      return;
    }

    const title =
      `World Cup Chat ${
        this.conversations().length + 1
      }`;

    this.chatService
      .createConversation(
        email,
        title
      )
      .subscribe({

        next: (conversation) => {

          this.conversations.update(
            list => [
              conversation,
              ...list
            ]
          );

          this.selectedConversation.set(
            conversation
          );

          this.messages.set([]);

          this.cdr.detectChanges();

        },

        error: (err) => {

          console.error(err);

          this.error.set(
            'Failed to create conversation'
          );

        }

      });

  }

  selectConversation(
    conversation: Conversation
  ): void {

    this.selectedConversation.set(
      conversation
    );

    this.loading.set(true);

    this.chatService
      .getMessages(
        conversation.id
      )
      .subscribe({

        next: (messages) => {

          this.messages.set(
            messages
          );

          this.loading.set(false);

          this.cdr.detectChanges();

          setTimeout(
            () => this.scrollToBottom(),
            50
          );

        },

        error: (err) => {

          console.error(err);

          this.loading.set(false);

          this.error.set(
            'Failed to load messages'
          );

        }

      });

  }

  sendMessage(): void {

    const text =
      this.prompt().trim();

    const conversation =
      this.selectedConversation();

    if (
      !text ||
      !conversation ||
      this.sending()
    ) {
      return;
    }

    const optimisticMessage: Message = {

      conversation_id:
      conversation.id,

      role: 'user',

      content: text

    };

    this.messages.update(
      messages => [
        ...messages,
        optimisticMessage
      ]
    );

    this.prompt.set('');

    this.sending.set(true);

    this.scrollToBottom();

    this.chatService
      .sendMessage({

        conversation_id:
        conversation.id,

        message: text

      })
      .subscribe({

        next: () => {

          this.chatService
            .getMessages(
              conversation.id
            )
            .subscribe({

              next: (messages) => {

                this.messages.set(
                  messages
                );

                this.sending.set(false);

                this.cdr.detectChanges();

                setTimeout(
                  () => this.scrollToBottom(),
                  50
                );

              },

              error: (err) => {

                console.error(err);

                this.sending.set(false);

              }

            });

        },

        error: (err) => {

          console.error(err);

          this.sending.set(false);

          this.error.set(
            'Failed to send message'
          );

        }

      });

  }

  deleteConversation(
    conversation: Conversation
  ): void {

    this.chatService
      .deleteConversation(
        conversation.id
      )
      .subscribe({

        next: () => {

          this.conversations.update(
            conversations =>
              conversations.filter(
                c =>
                  c.id !==
                  conversation.id
              )
          );

          if (
            this.selectedConversation()?.id ===
            conversation.id
          ) {

            this.selectedConversation.set(
              null
            );

            this.messages.set([]);

          }

          this.cdr.detectChanges();

        },

        error: (err) => {

          console.error(err);

          this.error.set(
            'Failed to delete conversation'
          );

        }

      });

  }

  scrollToBottom(): void {

    if (
      !this.messagesContainer
    ) {
      return;
    }

    const element =
      this.messagesContainer
        .nativeElement;

    element.scrollTop =
      element.scrollHeight;

  }

  trackConversation(
    index: number,
    conversation: Conversation
  ): string {

    return conversation.id;

  }

}
