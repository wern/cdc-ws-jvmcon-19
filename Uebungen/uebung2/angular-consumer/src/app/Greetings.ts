export class Greetings {
  greetings: Greeting[];
}

export class Greeting {
  type: string;
  phrase: string;

  constructor(type: string, phrase: string) {
    this.type = type;
    this.phrase = phrase;
  }
}

export class GreetingResponse {
  greeting: Greeting;
}
