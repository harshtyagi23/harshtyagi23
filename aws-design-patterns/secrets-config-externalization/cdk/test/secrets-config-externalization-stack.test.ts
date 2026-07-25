import { App } from 'aws-cdk-lib';
import { Template } from 'aws-cdk-lib/assertions';
import { SecretsConfigExternalizationStack } from '../lib/secrets-config-externalization-stack';

describe('SecretsConfigExternalizationStack', () => {
  it('creates Lambda, secret, and parameter', () => {
    const app = new App();
    const stack = new SecretsConfigExternalizationStack(app, 'SecretsConfigExternalizationStack');
    const template = Template.fromStack(stack);

    template.resourceCountIs('AWS::Lambda::Function', 1);
    template.resourceCountIs('AWS::SecretsManager::Secret', 1);
    template.resourceCountIs('AWS::SSM::Parameter', 1);
    template.hasResourceProperties('AWS::Lambda::Function', {
      Runtime: 'python3.12',
    });
  });
});