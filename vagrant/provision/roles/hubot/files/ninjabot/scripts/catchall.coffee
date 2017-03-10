regex_Match_First_Word = /([^\s]+)/i

answers = ["Beg your pardon? (try 'help').",
           "I don't know what that means.",
           "Excuse me, I didn't get it.",
           "I am sorry, but I don’t follow what you are saying",
           "I don't get it (try 'help').",
           "Would you mind clarifying?"]

module.exports = (robot) ->
  robot.catchAll (msg) ->
    match =  regex_Match_First_Word.exec(msg.message.text)
    match[1] = match[1].replace(":", "")
    if match[1].toUpperCase() == robot.name.toUpperCase()
      random = Math.floor(Math.random() * answers.length)
      msg.send answers[random]
