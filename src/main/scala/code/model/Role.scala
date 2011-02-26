package code.model

object Role extends Enumeration {
  type Role = Value
  val NoRole = Value("No Role")
  val Owner = Value("Owner")
  val Read  = Value("Read")
  val ReadWrite = Value("Read/Write")
}