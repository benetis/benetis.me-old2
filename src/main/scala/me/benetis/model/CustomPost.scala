package me.benetis.model

import eu.timepit.refined.types.string.NonEmptyString

case class PostFolderName(value: NonEmptyString) extends AnyVal
case class PostFileName(value: NonEmptyString) extends AnyVal

case class CustomPost(folderName: PostFolderName, postFileName: PostFileName)
