/*
 * FullUnorderedExpressionToScanPlanRule.java
 *
 * This source file is part of the FoundationDB open source project
 *
 * Copyright 2015-2019 Apple Inc. and the FoundationDB project authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.apple.foundationdb.record.query.plan.temp.rules;

import com.apple.foundationdb.record.query.plan.ScanComparisons;
import com.apple.foundationdb.record.query.plan.plans.RecordQueryScanPlan;
import com.apple.foundationdb.record.query.plan.temp.PlannerRule;
import com.apple.foundationdb.record.query.plan.temp.PlannerRuleCall;
import com.apple.foundationdb.record.query.plan.temp.expressions.FullUnorderedScanExpression;
import com.apple.foundationdb.record.query.plan.temp.matchers.ExpressionMatcher;
import com.apple.foundationdb.record.query.plan.temp.matchers.TypeMatcher;

import javax.annotation.Nonnull;

/**
 * A rule for implementing a {@link FullUnorderedScanExpression} as a {@link RecordQueryScanPlan} of the full primary
 * key space. This rule is used to generate "fallback" plans in the case that the planner does not find anything better.
 */
public class FullUnorderedExpressionToScanPlanRule extends PlannerRule<FullUnorderedScanExpression> {
    private static ExpressionMatcher<FullUnorderedScanExpression> root = TypeMatcher.of(FullUnorderedScanExpression.class);

    public FullUnorderedExpressionToScanPlanRule() {
        super(root);
    }

    @Nonnull
    @Override
    public ChangesMade onMatch(@Nonnull PlannerRuleCall call) {
        call.yield(call.ref(new RecordQueryScanPlan(ScanComparisons.EMPTY, false)));
        return ChangesMade.MADE_CHANGES;
    }
}
