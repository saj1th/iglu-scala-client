/*
 * Copyright (c) 2012-2016 Snowplow Analytics Ltd. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package com.snowplowanalytics.iglu.client
package validation

// circe
import io.circe.literal._

// This project
import ValidatableCirceMethods._

// Specs2
import org.specs2.Specification
import org.specs2.matcher.ValidatedMatchers

class SchemaValidationSpec extends Specification with ValidatedMatchers {
  def is = s2"""

  This is a specification to test Schema Validation

  validating a correct self-desc JSON should return the JSON in a Success $e1
  validating a correct self-desc JSON with JSON Schema with incorrect $$schema property should return Failure $e2
  """

  implicit val resolver = SpecHelpers.TestResolver

  val validJson =
    json"""{"schema": "iglu:com.snowplowanalytics.iglu-test/stock-item/jsonschema/1-0-0", "data": { "id": "123-12", "name": "t-shirt", "price": 29.99 } }"""

  val validJsonWithInvalidSchema =
    json"""{"schema": "iglu:com.snowplowanalytics.iglu-test/invalid-protocol/jsonschema/1-0-0", "data": { "id": 0 } }"""

  def e1 = validJson.validate(dataOnly = false) must beValid(validJson)

  def e2 = validJsonWithInvalidSchema.validate(dataOnly = false) must beInvalid

}
