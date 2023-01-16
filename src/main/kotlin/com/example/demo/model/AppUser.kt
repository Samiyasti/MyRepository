package com.example.demo.model

import java.util.*
import javax.persistence.*

@Entity(name = "app_users")
data class AppUser(
    @Id
    var id: String = UUID.randomUUID().toString(),
    @Column(unique = true, nullable = false)
    var username: String = "",
    @Column(nullable = false)
    var firstName: String = "",
    @Column(nullable = false)
    var lastName: String = "",
    @Column(nullable = false)
    var password: String = "",
    @Column(nullable = false)
    var role: String = "USER",
    @OneToMany(mappedBy = "appUser", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    var address: List<AppUserAddress> = ArrayList(),
    @ManyToMany(
        cascade = [
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH]
    )
    @JoinTable(
        name = "user_store",
        joinColumns = [JoinColumn(name = "app_user_id")],
        inverseJoinColumns = [JoinColumn(name = "store_id")]
    )
    var store: MutableList<UserStore> = ArrayList()

) {
    fun removeStore(store: List<UserStore>): AppUser {
        store.forEach { store ->
            run {
                this.store.remove(store)
                store.user.remove(this)
            }
        }
        return this
    }
}