package io.github.cmdq.sparrow.server

import java.util.*

public class Datastore {
    private var nextId = 0
    private val userTable = HashMap<Int, User>()

    public fun retrieveUser(id: Int):User? {
        return userTable[id]
    }

    public fun storeUser(user: User) {
        userTable[user.id] = user
    }

    public fun storeNewUser(newUser: UserCreation): Int {
        val user = User(
                id = nextId++,
                name = newUser.name,
                email = newUser.email,
                zipCode = newUser.zipCode,
                creationDate = Date().time
        )
        userTable[user.id] = user
        return user.id
    }
}