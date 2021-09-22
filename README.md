## Hash computing something, with worker process, and using Rabbit MQ

Tried with Heroku free plan, no tests, no clean code, no anything, just the ugly code, which hopefully does what excpected :D

(BTW i've checked out az MQ example source, which turned out to be a wrong choice, as its using quite old spring, and have a weird project settings, but whatever, i didn't want to fiddle with it... this is just a hobby like "tryout things" project, not for production, not to be reused, or continued :) )

CLOUDAMQP_URL environment variable should be set to a working rabbit MQ, com.txsanyix.inputQueue and com.txsanyix.outputQueue needs to be created. Others are hard coded.
Gets a batch of passwords, then computes the bcrypt hash for these, and sends them back. Once per application start.

Is this called as an easy task? :O Its probably easy for those who're working with distributed systems in cloud, every day, but for me, who didn't used such type of cloud service, nor developed distributed, process scalable application, it wasn't so easy, but at least, interesting :)